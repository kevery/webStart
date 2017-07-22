package com.kever.web.test;

import org.junit.Test;

import java.util.Properties;

public class CommonTest {
    @Test
    public void name() throws Exception {
        Properties properties = new Properties();
        properties.put("smart.framework.app.jsp_path", "/WEB-INF/view/");
    }
}
