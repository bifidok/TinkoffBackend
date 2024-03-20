package edu.java.scrapper.models.converters;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Convert;
import java.time.OffsetDateTime;

@Convert
public class OffsetDateTimeConverter implements AttributeConverter<OffsetDateTime,String> {
    @Override
    public String convertToDatabaseColumn(OffsetDateTime dateTime) {
        return dateTime.toString();
    }

    @Override
    public OffsetDateTime convertToEntityAttribute(String s) {
        String dateTimeTextWithT = s.substring(0,10) + "T" + s.substring(11);
        return OffsetDateTime.parse(dateTimeTextWithT);
    }
}
