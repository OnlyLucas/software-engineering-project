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

@Component
public class StringToTimestampConverter implements Converter<String, Timestamp> {
    private final Logger logger = LoggerFactory.getLogger(LoggingController.class);
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
