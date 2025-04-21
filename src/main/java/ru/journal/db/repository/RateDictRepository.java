package ru.journal.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.journal.db.entity.RateDictEntity;

public interface RateDictRepository extends JpaRepository<RateDictEntity, Long> {
}