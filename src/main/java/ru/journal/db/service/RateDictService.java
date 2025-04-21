package ru.journal.db.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.journal.db.entity.RateDictEntity;
import ru.journal.db.repository.RateDictRepository;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class RateDictService {
    private RateDictRepository repository;

    public void addRecord(RateDictEntity entity) {
        log.info("Добавление rate {} в БД", entity.getName());
        repository.save(entity);
    }

    public List<RateDictEntity> getAll() {
        return repository.findAll();
    }
}
