package ru.journal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.journal.domain.entity.RateEntity;

import java.time.LocalDate;
import java.util.List;

public interface RatesRepository extends JpaRepository<RateEntity, Long> {
    List<RateEntity> findAllByRateDate(LocalDate rateDate);
}