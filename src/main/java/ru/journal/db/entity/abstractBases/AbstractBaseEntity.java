package ru.journal.db.entity.abstractBases;

import jakarta.persistence.*;
import ru.journal.db.entity.interfaces.Identifiable;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class AbstractBaseEntity implements Identifiable {
    @Id
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @Override
    public Long getId() {
        return id;
    }
    @Override
    public void setId(Long id) {
        this.id=id;
    }
}
