package ru.journal.model.dto;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Component
@Getter
@Setter
@Schema(description = "Параметры фильтрации запроса на чтение журнала")
public class JournalFilter {
    @Parameter(description = "Дата курса", example = "rateDate=2025-05-17")
    private List<LocalDate> rateDate;

    @Parameter(description = "Название страны", example = "countryName=США")
    private List<String> countryName;

    @Parameter(description = "Код валюты", example = "charCode=USD")
    private List<String> charCode;

    @Parameter(description = "Номинал", example = "nominal=1")
    private List<Long> nominal;

    @Parameter(description = "Значение курса", example = "value=80.4137")
    private List<BigDecimal> value;
}
