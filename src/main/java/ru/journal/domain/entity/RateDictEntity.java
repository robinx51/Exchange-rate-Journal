package ru.journal.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import ru.journal.domain.entity.abstractBases.AbstractBaseDictEntity;

import java.io.Serializable;

@Entity
@Getter
@Setter
@AllArgsConstructor
@Table(name = "rate_dict")
public class RateDictEntity extends AbstractBaseDictEntity implements Serializable {
}
