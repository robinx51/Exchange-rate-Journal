package ru.journal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.journal.domain.entity.RateEntity;

public interface RatesRepository extends JpaRepository<RateEntity, Long> {
}