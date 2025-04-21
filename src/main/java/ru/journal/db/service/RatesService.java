package ru.journal.db.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.journal.db.entity.RateEntity;
import ru.journal.db.repository.RatesRepository;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class RatesService {
    private RatesRepository repository;

    public void addRecord(RateEntity entity) {
        log.info("Добавление rate {} в БД", entity.getCurrencyId());
        repository.save(entity);
    }

    public List<RateEntity> getAll() {
        return repository.findAll();
    }
}
