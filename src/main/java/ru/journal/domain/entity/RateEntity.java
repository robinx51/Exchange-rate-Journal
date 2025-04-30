package ru.journal.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import ru.journal.domain.entity.abstractBases.AbstractBaseFixationDateTimeEntity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@DiscriminatorValue("rates")
@Table(name = "rates")
public class RateEntity extends AbstractBaseFixationDateTimeEntity implements Serializable  {
    @Column(name = "currency_id")
    private String currencyId;

    @JoinColumn(name = "country_id")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private CountryEntity country;

    @JoinColumn(name = "rate_dict_id")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private RateDictEntity rateDict;

    @Column(name = "rate_date")
    private LocalDateTime rateDate;

    @Column(name = "nominal")
    private Long nominal;

    @Column(name = "value")
    private BigDecimal value;
}
