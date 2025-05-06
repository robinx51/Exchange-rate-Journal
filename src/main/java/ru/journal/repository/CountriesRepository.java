package ru.journal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.journal.domain.entity.CountryEntity;

public interface CountriesRepository extends JpaRepository<CountryEntity, Long> {
}