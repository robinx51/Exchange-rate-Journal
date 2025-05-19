package ru.journal.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Getter;
import lombok.Setter;
import ru.journal.dto.adapter.BigDecimalDeserializer;

import java.math.BigDecimal;

@Setter @Getter
public class ValuteDto {
    @JacksonXmlProperty(isAttribute = true, localName = "ID")
    private String id;

    @JacksonXmlProperty(localName = "NumCode")
    private int numCode;

    @JacksonXmlProperty(localName = "CharCode")
    private String charCode;

    @JacksonXmlProperty(localName = "Nominal")
    private Long nominal;

    @JacksonXmlProperty(localName = "Name")
    private String name;

    @JacksonXmlProperty(localName = "Value")
    @JsonDeserialize(using = BigDecimalDeserializer.class)
    private BigDecimal value;

    @JacksonXmlProperty(localName = "VunitRate")
    @JsonDeserialize(using = BigDecimalDeserializer.class)
    private BigDecimal vunitRate;
}
