package ru.journal.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import ru.journal.domain.entity.abstractBases.AbstractBaseDictEntity;

import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Builder
@AllArgsConstructor
@Table(name = "rate_dict")
public class RateDictEntity extends AbstractBaseDictEntity implements Serializable {
}
