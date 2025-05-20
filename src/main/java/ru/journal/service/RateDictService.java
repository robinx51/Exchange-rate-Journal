package ru.journal.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.journal.model.domain.entity.RateDictEntity;
import ru.journal.repository.RateDictRepository;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class RateDictService {
    private final RateDictRepository repository;

    public void save(RateDictEntity entity) {
        log.info("Добавление rate {} в БД", entity.getName());
        repository.save(entity);
    }

    public Optional<RateDictEntity> getByNumCode(int numCode) {
        return repository.getReferenceByNumCode(numCode);
    }
}
