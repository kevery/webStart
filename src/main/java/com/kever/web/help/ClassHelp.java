package com.kever.web.help;

import com.kever.web.annotation.Controller;
import com.kever.web.annotation.Service;
import com.kever.web.util.ClassUtil;

import java.util.HashSet;
import java.util.Set;

public class ClassHelp {
    /**
     * 定义类集合
     */
    private static final Set<Class<?>> CLASS_SET;


    static {
        String basePackage = ConfigHelp.getJdbcBasePackage();
        CLASS_SET = ClassUtil.getClassSet(basePackage);
    }

    /**
     * 获取所有类
     */
    public static Set<Class<?>> getClassSet() {
        return CLASS_SET;
    }

    /**
     * 获取所有service类
     */
    public static Set<Class<?>> getClassSetService() {
        Set<Class<?>> classSet = new HashSet<>();
        for (Class<?> aClass : CLASS_SET) {
            if (aClass.isAnnotationPresent(Service.class)) {
                classSet.add(aClass);
            }
        }
        return classSet;
    }

    /**
     * 获取所有controller类
     */
    public static Set<Class<?>> getClassSetController() {
        Set<Class<?>> classSet = new HashSet<>();
        for (Class<?> aClass : CLASS_SET) {
            if (aClass.isAnnotationPresent(Controller.class)) {
                classSet.add(aClass);
            }
        }
        return classSet;
    }

    /**
     * 获取所有bean类 (controller and service)
     */
    public static Set<Class<?>> getClassSetBeans() {
        Set<Class<?>> classSet = new HashSet<>();
        for (Class<?> aClass : CLASS_SET) {
            if (aClass.isAnnotationPresent(Service.class)||aClass.isAnnotationPresent(Controller.class)) {
                classSet.add(aClass);
            }
        }
        return classSet;
    }

}
