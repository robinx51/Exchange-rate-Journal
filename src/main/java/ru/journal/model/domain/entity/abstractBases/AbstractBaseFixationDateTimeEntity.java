package ru.journal.model.domain.entity.abstractBases;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import ru.journal.model.domain.entity.interfaces.FixationDateTime;

import java.time.LocalDateTime;

@Getter @Setter
@MappedSuperclass
public abstract class AbstractBaseFixationDateTimeEntity extends AbstractBaseEntity implements FixationDateTime {
    @Column(name = "created")
    protected LocalDateTime created;
    @Column(name = "updated")
    protected LocalDateTime updated;
}
