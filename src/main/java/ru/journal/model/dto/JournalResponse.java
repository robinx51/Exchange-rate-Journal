package ru.journal.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ru.journal.model.domain.entity.RateEntity;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@Schema(description = "Данные ответа на чтение журнала")
public class JournalResponse {
    @Schema(description = "Дата курса", example = "2023-05-20")
    private LocalDate rateDate;

    @Schema(description = "Код валюты", example = "USD")
    private String charCode;

    @Schema(description = "Название страны", example = "США")
    private String countryName;

    @Schema(description = "Название валюты", example = "Доллар США")
    private String rateName;

    @Schema(description = "Номинал", example = "1")
    private Long nominal;

    @Schema(description = "Значение курса", example = "80.4137")
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
