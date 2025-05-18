package ru.journal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import ru.journal.domain.entity.RateDictEntity;

import java.util.Optional;

public interface RateDictRepository extends JpaRepository<RateDictEntity, Long>, JpaSpecificationExecutor<RateDictEntity> {
    Optional<RateDictEntity> getReferenceByNumCode(int numCode);
}