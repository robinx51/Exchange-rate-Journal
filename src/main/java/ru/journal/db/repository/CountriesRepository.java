package ru.journal.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.journal.db.entity.CountryEntity;

public interface CountriesRepository extends JpaRepository<CountryEntity, Long> {
}