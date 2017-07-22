package com.kever.web.util;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public class PropsUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(PropsUtil.class);

    public static Properties loadProps(String fileName) {
        Properties props = null;
        InputStream inputStream = null;

        try {
            inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName);

            if (inputStream == null) {
                throw new FileNotFoundException(fileName + "file is not found");
            }

            props = new Properties();
            props.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
            LOGGER.error("load properties fail" + e);
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    LOGGER.error("close input stream failure", e);
                }

            }
        }
        return props;
    }

    public static String getString(Properties properties, String key) {
        return getString(properties, key, "");
    }

    private static String getString(Properties properties, String key, String defaultValue) {
        String value = defaultValue;
        if (properties.containsKey(key)) {
            value = properties.getProperty(key);
        }
        return value;
    }
}