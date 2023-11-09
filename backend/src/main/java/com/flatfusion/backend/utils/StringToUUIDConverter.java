package com.flatfusion.backend.utils;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.UUID;

@Component
public class StringToUUIDConverter implements Converter<String, UUID> {
    @Override
    public UUID convert(@NonNull String uuid) {
        try{
            return UUID.fromString(uuid);
        } catch (MethodArgumentTypeMismatchException e){
            return null;
        }
    }
}
