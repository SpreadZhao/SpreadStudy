package com.spread.recyclerviewstudy.tool.apm;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ReflectUtil {
    public static <T> T getHiddenField(Object instance, String name) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        Method metaMethod = Class.class.getDeclaredMethod("getDeclaredField", String.class);
        Field hiddenField = (Field) metaMethod.invoke(instance.getClass(), name);
        hiddenField.setAccessible(true);
        return (T) hiddenField.get(instance);
    }
}
