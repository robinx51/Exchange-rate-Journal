package ru.journal.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ru.journal.domain.entity.RateEntity;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
public class JournalResponse {
    private LocalDate rateDate;
    private String charCode;
    private String countryName;
    private String rateName;
    private Long nominal;
    private BigDecimal value;

    public JournalResponse(RateEntity entity) {
        this.charCode = entity.getCountry().getCharCode();
        this.countryName = entity.getCountry().getName();
        this.rateName = entity.getRateDict().getName();
        this.rateDate = entity.getRateDate();
        this.nominal = entity.getNominal();
        this.value = entity.getValue();
    }
}
