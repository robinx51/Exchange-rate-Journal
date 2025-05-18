package ru.journal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import ru.journal.domain.entity.CountryEntity;

import java.util.Optional;

public interface CountriesRepository extends JpaRepository<CountryEntity, Long>, JpaSpecificationExecutor<CountryEntity> {
    Optional<CountryEntity> getReferenceByNumCode(int numCode);

    CountryEntity getReferenceByName(String name);
}