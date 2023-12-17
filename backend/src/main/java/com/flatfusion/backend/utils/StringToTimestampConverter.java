package com.flatfusion.backend.utils;

import com.flatfusion.backend.controllers.LoggingController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.UUID;

/**
 * Custom Spring Converter implementation for converting a String to a Timestamp.
 * This converter is used to parse string representations of timestamps and convert them to Timestamp objects.
 * If the conversion fails, an IllegalArgumentException is thrown with appropriate error logging.
 */
@Component
public class StringToTimestampConverter implements Converter<String, Timestamp> {
    private final Logger logger = LoggerFactory.getLogger(LoggingController.class);

    /**
     * Converts the provided String source to a Timestamp.
     *
     * @param source The input String to be converted to a Timestamp.
     * @return The Timestamp object representing the parsed time.
     * @throws IllegalArgumentException If the provided String cannot be converted to a Timestamp.
     */
    @Override
    public Timestamp convert(String source) {
        logger.debug("StringToTimestampConverter called.");
        try{
            return Timestamp.valueOf(source);
        } catch (IllegalArgumentException e){
            logger.error("Error converting string to timestamp: " + source, e);
            throw e;
        }
    }
}
