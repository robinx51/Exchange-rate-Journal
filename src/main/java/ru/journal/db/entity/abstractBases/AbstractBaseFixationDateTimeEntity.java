package ru.journal.db.entity.abstractBases;

import jakarta.persistence.*;
import ru.journal.db.entity.interfaces.FixationDateTime;

import java.time.LocalDateTime;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class AbstractBaseFixationDateTimeEntity extends AbstractBaseEntity implements FixationDateTime {
    @Column(name = "created")
    protected LocalDateTime created;

    @Column(name = "updated")
    protected LocalDateTime updated;
}
