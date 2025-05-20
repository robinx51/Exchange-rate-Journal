package ru.journal.model.dto.adapter;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.util.Locale;

public class BigDecimalDeserializer extends JsonDeserializer<BigDecimal> {
    private static final DecimalFormat DECIMAL_FORMAT;

    static {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.getDefault());
        symbols.setDecimalSeparator(',');
        DECIMAL_FORMAT = new DecimalFormat();
        DECIMAL_FORMAT.setDecimalFormatSymbols(symbols);
        DECIMAL_FORMAT.setParseBigDecimal(true);
    }

    @Override
    public BigDecimal deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        try {
            return (BigDecimal) DECIMAL_FORMAT.parse(p.getText());
        } catch (ParseException e) {
            throw new IOException("Failed to parse BigDecimal", e);
        }
    }
}
