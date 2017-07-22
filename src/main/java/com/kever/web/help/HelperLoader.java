package com.kever.web.help;

import com.kever.web.util.ClassUtil;

public class HelperLoader {
    public static void init() {
        Class<?>[] classList = {ClassHelp.class, BeanHelp.class, IocHelp.class, ControllerHelp.class};
        for (Class<?> aClass : classList) {
            ClassUtil.loadClass(aClass.getName(),true);
        }
    }
}
