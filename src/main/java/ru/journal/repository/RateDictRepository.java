package ru.journal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.journal.domain.entity.RateDictEntity;

import java.util.Optional;

public interface RateDictRepository extends JpaRepository<RateDictEntity, Long> {
    Optional<RateDictEntity> getReferenceByNumCode(int numCode);
}