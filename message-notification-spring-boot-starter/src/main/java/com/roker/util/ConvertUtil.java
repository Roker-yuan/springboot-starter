package com.roker.util;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ConvertUtil {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static <T> T convertObject(Object object, Class<T> clazz) {
        return objectMapper.convertValue(object, clazz);
    }
}