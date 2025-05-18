package ru.journal.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Component
@Getter
@Setter
public class JournalFilter {
    private List<LocalDate> rateDate;
    private List<String> countryName;
    private List<String> charCode;
    private List<Long> nominal;
    private List<BigDecimal> value;
}
