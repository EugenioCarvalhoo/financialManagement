package com.carvalho.solution.util;

import java.lang.reflect.Field;

import com.carvalho.solution.exception.AppBusinessException;

public class ReflectionUtil {

    public static void setFieldValue(Object object, String fieldName, Object value) {
        try {
            Field field = object.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(object, value);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new AppBusinessException("Erro ao setar o valor do campo: " + fieldName);
        }
    }
}