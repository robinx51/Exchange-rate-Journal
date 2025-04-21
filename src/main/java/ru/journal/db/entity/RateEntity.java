package ru.journal.db.entity;

import jakarta.persistence.*;
import lombok.*;
import ru.journal.db.entity.abstractBases.AbstractBaseFixationDateTimeEntity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@DiscriminatorValue("rates")
@Table(name = "rates")
public class RateEntity extends AbstractBaseFixationDateTimeEntity implements Serializable  {
    @Column(name = "currency_id")
    private String currencyId;

    //private Long countryId;

    @JoinColumn(name = "country_id")
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    private CountryEntity country;

    //private Long rateDictId;

    @JoinColumn(name = "rate_dict_id")
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    private RateDictEntity rateDict;

    @Column(name = "rate_date")
    private LocalDateTime rateDate;

    @Column(name = "nominal")
    private Long nominal;

    @Column(name = "value")
    private BigDecimal value;

    @Override
    public LocalDateTime getCreated() {
        return created;
    }
    @Override
    public void setCreated(LocalDateTime created) {
        this.created = created;
    }
    @Override
    public LocalDateTime getUpdated() {
        return updated;
    }
    @Override
    public void setUpdated(LocalDateTime updated) {
        this.updated = updated;
    }
}
