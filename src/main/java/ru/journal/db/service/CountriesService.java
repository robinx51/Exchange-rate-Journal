package ru.journal.db.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.journal.db.entity.CountryEntity;
import ru.journal.db.repository.CountriesRepository;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class CountriesService {
    private CountriesRepository repository;

    public void addRecord(CountryEntity entity) {
        log.info("Добавление rate {} в БД", entity.getName());
        repository.save(entity);
    }

    public List<CountryEntity> getAll() {
        return repository.findAll();
    }
}
