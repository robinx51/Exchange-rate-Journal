package ru.journal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.journal.domain.entity.RateDictEntity;

public interface RateDictRepository extends JpaRepository<RateDictEntity, Long> {
}