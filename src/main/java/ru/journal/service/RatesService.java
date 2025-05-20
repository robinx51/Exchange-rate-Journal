package ru.journal.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.journal.model.domain.entity.RateEntity;
import ru.journal.model.dto.JournalFilter;
import ru.journal.model.dto.JournalResponse;
import ru.journal.repository.RatesRepository;
import ru.journal.specification.RatesSpecification;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class RatesService {
    private final RatesRepository repository;

    public List<JournalResponse> findAll(JournalFilter filter, int page, int size, String[] sort) {
        Pageable pageable = createPageRequest(page, size, sort);
        return filteredSearch(filter, pageable)
                .map(JournalResponse::new)
                .getContent();
    }

    public void saveAll(List<RateEntity> entities) {
        repository.saveAll(entities);
    }

    public List<RateEntity> getByRateDate(LocalDate date) {
        return repository.findAllByRateDate(date);
    }

    public Page<RateEntity> filteredSearch(JournalFilter criteria, Pageable pageable) {
        Specification<RateEntity> spec = Specification
                .where(RatesSpecification.likeRateDates(criteria.getRateDate()))
                .and(RatesSpecification.likeCountries(criteria.getCountryName()))
                .and(RatesSpecification.likeRateCodes(criteria.getCharCode()))
                .and(RatesSpecification.likeNominal(criteria.getNominal()))
                .and(RatesSpecification.likeValues(criteria.getValue()));
        return repository.findAll(spec, pageable);
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
