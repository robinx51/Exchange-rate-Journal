package ru.journal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import ru.journal.model.domain.entity.RateEntity;

import java.time.LocalDate;
import java.util.List;

public interface RatesRepository extends JpaRepository<RateEntity, Long>, JpaSpecificationExecutor<RateEntity> {
    List<RateEntity> findAllByRateDate(LocalDate rateDate);
}