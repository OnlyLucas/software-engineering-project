package com.flatfusion.backend.utils;

import com.flatfusion.backend.controllers.LoggingController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import java.util.UUID;

@Component
public class StringToUUIDConverter implements Converter<String, UUID> {

    private final Logger logger = LoggerFactory.getLogger(LoggingController.class);
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
