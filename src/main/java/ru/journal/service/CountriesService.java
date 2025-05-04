package ru.journal.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.journal.domain.entity.CountryEntity;
import ru.journal.repository.CountriesRepository;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class CountriesService {
    private final CountriesRepository repository;

    public void save(CountryEntity entity) {
        log.info("Добавление rate {} в БД", entity.getName());
        repository.save(entity);
    }

    public Optional<CountryEntity> getByNumCode(int numCode) {
        return repository.getReferenceByNumCode(numCode);
    }
}
