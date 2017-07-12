package com.kever.web.help;

import com.kever.web.annotation.Inject;
import com.kever.web.util.ReflectionUtil;

import java.lang.reflect.Field;
import java.util.Map;

public final class IocHelp {

    static {
        //获取所有bean 映射关系
        Map<Class<?>, Object> beanMap = BeanHelp.getBeanMap();

        if (!(beanMap == null || beanMap.isEmpty())) {
            for (Map.Entry<Class<?>, Object> entry : beanMap.entrySet()) {
                Class<?> beanClass = entry.getKey();
                Object instance = entry.getValue();

                Field[] beanField = beanClass.getDeclaredFields();

                for (Field field : beanField) {
                    if (field.isAnnotationPresent(Inject.class)) {
                        Class<?> type = field.getType();
                        Object beanFieldInstance = beanMap.get(type);
                        if (beanFieldInstance != null) {
                            ReflectionUtil.setFied(instance, field, beanFieldInstance);
                        }

                    }

                }
            }
        }
    }
}
