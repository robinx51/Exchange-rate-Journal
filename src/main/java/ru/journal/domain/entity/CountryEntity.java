package ru.journal.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import ru.journal.domain.entity.abstractBases.AbstractBaseDictEntity;

import java.io.Serializable;

@Entity
@Builder
@AllArgsConstructor
@Table(name = "countries")
public class CountryEntity  extends AbstractBaseDictEntity implements Serializable {
}
