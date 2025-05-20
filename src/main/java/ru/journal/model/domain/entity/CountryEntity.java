package ru.journal.model.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import ru.journal.model.domain.entity.abstractBases.AbstractBaseDictEntity;

import java.io.Serializable;

@Entity
@Getter
@Setter
@AllArgsConstructor
@Table(name = "countries")
public class CountryEntity  extends AbstractBaseDictEntity implements Serializable {
}
