package ru.journal.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.journal.domain.entity.RateEntity;
import ru.journal.repository.RatesRepository;

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
}
