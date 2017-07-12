package com.kever.web.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class ReflectionUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(ReflectionUtil.class);

    /**
     * 创建实例
     */
    public static Object newInstance(Class<?> clazz) {
        Object object;
        try {
            object = clazz.newInstance();
        } catch (Exception e) {
            LOGGER.error(clazz.getName() + "newInstance fail", e);
            throw new RuntimeException(e);
        }
        return object;
    }

    /**
     * 调用方法
     */
    public static Object invokeMethod(Object obj, Method method, Object... params) {
        Object result;
        try {
            method.setAccessible(true);
            result = method.invoke(obj, params);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
        return result;
    }

    /**
     * 设置成员变量的值
     */
    public static  void setFied(Object obj, Field field, Object value) {
        field.setAccessible(true);
        try {
            field.set(obj, value);
        } catch (Exception e) {
            LOGGER.error("set field value fail" + e);
            throw new RuntimeException(e);
        }
    }
}
