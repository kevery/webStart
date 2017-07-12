package com.kever.web.util;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;

public class ClassUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(ClassUtil.class);


    public static ClassLoader getClassLoader() {
        return Thread.currentThread().getContextClassLoader();
    }

    public static Class<?> loadClass(String className, boolean isInit) {
        Class<?> clazz = null;

        try {
            clazz = Class.forName(className, isInit, getClassLoader());
        } catch (ClassNotFoundException e) {
            LOGGER.error("load class " + className + " fail", e);
        }
        return clazz;
    }

    public static void main(String[] args) {
        getClassSet("com.kever.web");
    }


    /**
     * 获取指定包名下所有类
     */
    public static Set<Class<?>> getClassSet(String packageName) {
        Set<Class<?>> classSet = new HashSet<>();

        try {
            Enumeration<URL> resources = getClassLoader().getResources(packageName.replace(".", "/"));
            while (resources.hasMoreElements()) {
                URL url = resources.nextElement();
                if (url != null) {
                    String protocol = url.getProtocol();
                    if ("file".equals(protocol)) {
                        String packagePath = url.getPath().replaceAll("%20", "");
                        addClazz(classSet, packagePath, packageName);

                    } else if ("jar".equals(protocol)) {
                        // TODO: 2017/7/12 0012
                    }

                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return classSet;

    }


    public static void addClazz(Set<Class<?>> classSet,String packagePath, String packageName) {
        File[] files = new File(packagePath).listFiles(new FileFilter() {
            @Override
            public boolean accept(File file) {
                return (file.isFile() && file.getName().endsWith(".class")) || file.isDirectory();
            }
        });

        if (files != null) {
            for (File file : files) {
                String fileName = file.getName();
                if (file.isFile()) {
                    String className = fileName.substring(0, fileName.lastIndexOf("."));
                    if (StringUtils.isNotEmpty(className)) {
                        className = packageName + "." + className;
                    }
                    doAddClass(classSet, className);
                }else{
                    String subPackagePath = fileName;
                    if (StringUtils.isNotEmpty(subPackagePath)) {
                        subPackagePath = packagePath + "/" + subPackagePath;
                    }
                    String subPackageName = fileName;
                    if (StringUtils.isNotEmpty(subPackageName)) {
                        subPackageName = packageName + "." + subPackageName;
                    }
                    addClazz(classSet, subPackagePath, subPackageName);
                }
            }
        }
    }


    public static void doAddClass(Set<Class<?>> classSet, String className) {
        classSet.add(loadClass(className, false));
    }
}
