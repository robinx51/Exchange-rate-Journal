package ru.journal.db.entity.abstractBases;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class AbstractBaseDictEntity extends AbstractBaseEntity {
    @Column(name = "name")
    private String name;
    @Column(name = "num_code")
    private int numCode;
    @Column(name = "char_code")
    private String charCode;
}
