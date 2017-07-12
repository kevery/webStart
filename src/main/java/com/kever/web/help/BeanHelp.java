package com.kever.web.help;

import com.kever.web.util.ReflectionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class BeanHelp {
    private static final Logger LOGGER = LoggerFactory.getLogger(BeanHelp.class);


    private static final Map<Class<?>, Object> BEAN_MAP = new HashMap<>();

    static {
        Set<Class<?>> classSetBeans = ClassHelp.getClassSetBeans();
        for (Class<?> beanClass : classSetBeans) {
            Object instance = ReflectionUtil.newInstance(beanClass);
            BEAN_MAP.put(beanClass, instance);
        }
    }

    /**
     * 获取映射
     */
    public static Map<Class<?>, Object> getBeanMap() {
        return BEAN_MAP;
    }

    /**
     * 获取bean实例
     */
    @SuppressWarnings("unchecked")
    public static <T> T getBean(Class<T> clazz) {
        if (!BEAN_MAP.containsKey(clazz)) {
            throw new RuntimeException("can not get bean by class" + clazz);
        }
        return (T) BEAN_MAP.get(clazz);
    }
}
