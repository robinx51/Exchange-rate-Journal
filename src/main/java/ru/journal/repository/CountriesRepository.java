package ru.journal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.journal.domain.entity.CountryEntity;

import java.util.Optional;

public interface CountriesRepository extends JpaRepository<CountryEntity, Long> {
    Optional<CountryEntity> getReferenceByNumCode(int numCode);

    CountryEntity getReferenceByName(String name);
}