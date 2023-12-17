package com.flatfusion.backend.utils;

import com.flatfusion.backend.controllers.LoggingController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import java.util.UUID;

/**
 * Custom Spring Converter implementation for converting a String to a UUID.
 * This converter is used to parse string representations of UUIDs and convert them to UUID objects.
 * If the conversion fails, a MethodArgumentTypeMismatchException is thrown with appropriate error logging.
 */
@Component
public class StringToUUIDConverter implements Converter<String, UUID> {

    private final Logger logger = LoggerFactory.getLogger(LoggingController.class);

    /**
     * Converts the provided String UUID representation to a UUID object.
     *
     * @param uuid The input String to be converted to a UUID.
     * @return The UUID object representing the parsed UUID.
     * @throws MethodArgumentTypeMismatchException If the provided String cannot be converted to a UUID.
     */
    @Override
    public UUID convert(@NonNull String uuid) {
        logger.debug("StringToUUIDConverter called.");
        try{
            return UUID.fromString(uuid);
        } catch (MethodArgumentTypeMismatchException e){
            logger.error("Error converting String to UUID: " + uuid, e);
            throw e;
        }
    }
}
