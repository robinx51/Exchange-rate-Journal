package ru.journal.domain.entity.interfaces;

import java.time.LocalDateTime;

public interface FixationDateTime {
    LocalDateTime getCreated();
    void setCreated(LocalDateTime created);
    LocalDateTime getUpdated();
    void setUpdated(LocalDateTime updated);
}
