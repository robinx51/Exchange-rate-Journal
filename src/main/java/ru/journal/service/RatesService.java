package ru.journal.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.journal.domain.entity.RateEntity;
import ru.journal.dto.JournalFilter;
import ru.journal.repository.RatesRepository;
import ru.journal.specification.RatesSpecification;

import java.time.LocalDate;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class RatesService {
    private final RatesRepository repository;

    public void save(RateEntity entity) {
        log.info("Добавление rate {} в БД", entity.getCurrencyId());
        repository.save(entity);
    }

    public void saveAll(List<RateEntity> entities) {
        repository.saveAll(entities);
    }

    public List<RateEntity> getByRateDate(LocalDate date) {
        return repository.findAllByRateDate(date);
    }

    public List<RateEntity> filteredSearch(JournalFilter criteria) {
        Specification<RateEntity> spec = Specification
                .where(RatesSpecification.likeRateDates(criteria.getRateDate()))
                .and(RatesSpecification.likeCountries(criteria.getCountryName()))
                .and(RatesSpecification.likeRateCodes(criteria.getCharCode()))
                .and(RatesSpecification.likeNominal(criteria.getNominal()))
                .and(RatesSpecification.likeValues(criteria.getValue()));
        return repository.findAll(spec);
    }
}
