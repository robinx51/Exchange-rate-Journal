package ru.journal.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.xml.ser.ToXmlGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.journal.model.domain.entity.CountryEntity;
import ru.journal.model.domain.entity.RateDictEntity;
import ru.journal.model.domain.entity.RateEntity;
import ru.journal.model.dto.CBRDto;
import ru.journal.model.dto.JournalFilter;
import ru.journal.model.dto.JournalResponse;
import ru.journal.model.dto.ValuteDto;
import ru.journal.feign.CBRFeignClient;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service @Slf4j
@RequiredArgsConstructor
public class CBRService {
    /// TODO:
    ///
    /// - чтение данных справочника стран-носителей валюты;
    /// - чтение данных справочника валюты;
    /// - редактирование курса валют.

    private final CBRFeignClient cbrFeignClient;
    private final RatesService ratesService;
    private final CountriesService countriesService;
    private final RateDictService rateDictService;


    public ResponseEntity<List<JournalResponse>> handleRates(JournalFilter filter, int page, int size, String[] sort) {
        Pageable pageable = createPageRequest(page, size, sort);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ratesService.filteredSearch(filter, pageable)
                        .map(JournalResponse::new)
                        .getContent()
                );
    }

    @Transactional
    public boolean updateRates() {
        CBRDto response;
        try {
            response = getRates();
        }
        catch (Exception e) {
            log.error("XML parsed with error: ", e);
            return false;
        }
        if (response == null) {
            log.error("Received null response");
            return false;
        }
        log.debug("XML successfully parsed");
        DateTimeFormatter pattern = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        LocalDate rateDate = LocalDate.parse(response.getDate(), pattern);
        List<RateEntity> rates = ratesService.getByRateDate(rateDate);

        if (rates.isEmpty()) {
            rates = createRates(rateDate, response.getValutes());
        }
        else {
            updateRates(rates, response.getValutes());
        }

        ratesService.saveAll(rates);
        log.debug("Updated {} rates", rates.size());
        return true;
    }

    private List<RateEntity> createRates(LocalDate rateDate, List<ValuteDto> valutes) {
        List<RateEntity> rates = new ArrayList<>();
        for (ValuteDto valute : valutes) {
            RateEntity rate = new RateEntity();
            rate.setCreated(LocalDateTime.now());
            rate.setCurrencyId(valute.getId());
            rate.setCountry(getCountry(valute));
            rate.setRateDict(getRateDict(valute));
            rate.setRateDate(rateDate);
            rate.setNominal(valute.getNominal());
            rate.setValue(valute.getValue());

            rates.add(rate);
        }
        return rates;
    }

    private void updateRates(List<RateEntity> rates, List<ValuteDto> valutes) {
        Map<Integer, BigDecimal> valuesMap = getValuesMap(valutes);
        for (RateEntity rate : rates) {
            rate.setUpdated(LocalDateTime.now());
            rate.setValue(valuesMap.get(rate.getCountry().getNumCode()));
        }
    }

    private Map<Integer, BigDecimal> getValuesMap(List<ValuteDto> valutes) {
        return valutes.stream()
                .collect(Collectors.toMap(ValuteDto::getNumCode, ValuteDto::getValue));
    }

    private CountryEntity getCountry(ValuteDto valute) {
        int numCode = valute.getNumCode();
        Optional<CountryEntity> entity = countriesService.getByNumCode(numCode);
        return entity.orElseGet(() -> countriesService.getByName("unknown"));
    }

    private RateDictEntity getRateDict(ValuteDto valute) {
        int numCode = valute.getNumCode();
        Optional<RateDictEntity> entity = rateDictService.getByNumCode(numCode);
        return entity.orElseGet(() -> {
            RateDictEntity rateDict = new RateDictEntity();
            rateDict.setName(valute.getName());
            rateDict.setNumCode(numCode);
            rateDict.setCharCode(valute.getCharCode());

            rateDictService.save(rateDict);
            return rateDict;
        });
    }

    private CBRDto getRates() {
        log.debug("Requesting to CBR");
        String xml = cbrFeignClient.getRates();
        byte[] utf8Bytes = xml.getBytes(StandardCharsets.ISO_8859_1);
        String utf8Xml = new String(utf8Bytes, StandardCharsets.UTF_8);

        XmlMapper xmlMapper = new XmlMapper();
        xmlMapper.configure(ToXmlGenerator.Feature.WRITE_XML_DECLARATION, true);
        try {
            return xmlMapper.readValue(utf8Xml, CBRDto.class);
        } catch (JsonProcessingException e) {
            return null;
        }
    }

    private Pageable createPageRequest(int page, int size, String[] sort) {
        List<Sort.Order> orders = new ArrayList<>();

        for (String sortOrder : sort) {
            String[] _sort = sortOrder.split(":");
            orders.add(new Sort.Order(
                    _sort.length > 1 ? Sort.Direction.fromString(_sort[1]) : Sort.Direction.DESC,
                    _sort[0]
            ));
        }

        return PageRequest.of(page, size, Sort.by(orders));
    }
}
