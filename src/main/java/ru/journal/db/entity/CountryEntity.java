package ru.journal.db.entity;

import jakarta.persistence.*;
import lombok.*;
import ru.journal.db.entity.abstractBases.AbstractBaseDictEntity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Builder
@AllArgsConstructor
@Table(name = "countries")
public class CountryEntity  extends AbstractBaseDictEntity implements Serializable {
    public List<RateEntity> rates(){
        return new ArrayList<>();
    }
}
