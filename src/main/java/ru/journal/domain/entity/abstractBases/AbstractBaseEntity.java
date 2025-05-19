package ru.journal.domain.entity.abstractBases;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import ru.journal.domain.entity.interfaces.Identifiable;

@Getter @Setter
@MappedSuperclass
public abstract class AbstractBaseEntity implements Identifiable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Integer id;
}
