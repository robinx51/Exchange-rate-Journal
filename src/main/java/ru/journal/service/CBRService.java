package ru.journal.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.xml.ser.ToXmlGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.journal.domain.entity.CountryEntity;
import ru.journal.domain.entity.RateDictEntity;
import ru.journal.domain.entity.RateEntity;
import ru.journal.dto.CBRDto;
import ru.journal.dto.ValuteDto;
import ru.journal.feign.CBRFeignClient;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service @Slf4j
@RequiredArgsConstructor
public class CBRService {
    /// TODO:
    /// - автоматическая синхронизация курса валют с ЦБ по расписанию;
    /// - ручной запуск синхронизации курса валют с ЦБ;
    /// - чтение журнала с фильтрацией, пагинацией и сортировкой по параметрам;
    /// - чтение данных справочника стран-носителей валюты;
    /// - чтение данных справочника валюты;
    /// - редактирование курса валют.

    private final CBRFeignClient cbrFeignClient;
    private final RatesService ratesService;
    private final CountriesService countriesService;
    private final RateDictService rateDictService;

    public ResponseEntity<String> handleRates() {
        CBRDto response;
        try {
            response = getRates();
        }
        catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error parsing XML response");
        }
        if (response == null) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error parsing XML response");
        }
        log.debug("XML successfully parsed");

        List<RateEntity> rates = ratesService.getAll();
        LocalDateTime rateDate = LocalDateTime.now();

        if (rates.isEmpty())
            rates = createRates(rateDate, response.getValutes());
        else
            updateRates(rateDate, rates, response.getValutes());

        ratesService.saveAll(rates);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body("Success");
    }

    private void updateRates(LocalDateTime date, List<RateEntity> rates, List<ValuteDto> valutes) {
        Map<Integer, BigDecimal> values = getValuesMap(valutes);
        for (RateEntity rate : rates) {
            rate.setUpdated(LocalDateTime.now());
            rate.setNominal(rate.getNominal());
            rate.setValue(values.get(rate.getId()));
            rate.setRateDate(date);
        }
    }

    private List<RateEntity> createRates(LocalDateTime rateDate, List<ValuteDto> valutes) {
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

    private Map<Integer, BigDecimal> getValuesMap(List<ValuteDto> valutes) {
        return valutes.stream()
                .collect(Collectors.toMap(ValuteDto::getNumCode, ValuteDto::getValue));
    }

    private CountryEntity getCountry(ValuteDto valute) {
        int numCode = valute.getNumCode();
        Optional<CountryEntity> entity = countriesService.getByNumCode(numCode);
        return entity.orElseGet(() -> {
            CountryEntity country = new CountryEntity();
            country.setNumCode(numCode);
            country.setName(valute.getName());
            country.setCharCode(valute.getCharCode());

            countriesService.save(country);
            return country;
        });
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
}
