package ru.journal.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.journal.db.entity.RateEntity;

public interface RatesRepository extends JpaRepository<RateEntity, Long> {
}