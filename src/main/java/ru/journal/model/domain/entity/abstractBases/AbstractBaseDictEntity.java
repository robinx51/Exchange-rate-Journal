package ru.journal.model.domain.entity.abstractBases;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@MappedSuperclass
public abstract class AbstractBaseDictEntity extends AbstractBaseEntity {
    @Column(name = "name")
    private String name;
    @Column(name = "num_code")
    private int numCode;
    @Column(name = "char_code")
    private String charCode;
}
