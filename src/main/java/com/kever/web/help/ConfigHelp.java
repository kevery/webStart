package com.kever.web.help;

import com.kever.web.util.ConfigConstant;
import com.kever.web.util.PropsUtil;

import java.util.Properties;

public class ConfigHelp {
    private static final Properties CONFIG_PROPS = PropsUtil.loadProps(ConfigConstant.CONFIG_FILE);


    public static String getJdbcDriver() {
        return PropsUtil.getString(CONFIG_PROPS, ConfigConstant.JDBC_DRIVER);
    }

    public static String getJdbcUrl() {
        return PropsUtil.getString(CONFIG_PROPS, ConfigConstant.JDBC_URL);
    }

    public static String getJdbcUser() {
        return PropsUtil.getString(CONFIG_PROPS, ConfigConstant.JDBC_USER);
    }

    public static String getJdbcPassword() {
        return PropsUtil.getString(CONFIG_PROPS, ConfigConstant.JDBC_PASSWORD);
    }

    public static String getJdbcBasePackage() {
        return PropsUtil.getString(CONFIG_PROPS, ConfigConstant.BASE_PACKAGE);
    }
    public static String getJdbcJspPath() {
        return PropsUtil.getString(CONFIG_PROPS, ConfigConstant.JSP_PATH);
    }
    public static String getJdbcAssertPath() {
        return PropsUtil.getString(CONFIG_PROPS, ConfigConstant.ASSERT_PATH);
    }
}
