package com.kever.web.help;

import com.kever.web.annotation.Action;
import com.kever.web.bean.Handle;
import com.kever.web.bean.Request;
import com.kever.web.util.CollectionUtil;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ControllerHelp {
    private static final Map<Request, Handle> REQUEST_HANDLE_MAP = new HashMap<>();

    static {
        Set<Class<?>> classSetController = ClassHelp.getClassSetController();
        if (CollectionUtil.isNotEmpty(classSetController)) {
            for (Class<?> controllerClass : classSetController) {
                Method[] declaredMethods = controllerClass.getDeclaredMethods();
                if (declaredMethods.length > 0) {
                    for (Method declaredMethod : declaredMethods) {
                        if (declaredMethod.isAnnotationPresent(Action.class)) {
                            Action action = declaredMethod.getAnnotation(Action.class);
                            String mapping = action.value();
                            if (mapping.matches("\\w+:/\\w*")){
                                String[] array = mapping.split(":");
                                String requestMethod = array[0];
                                String requestPath = array[1];
                                Request request = new Request(requestMethod, requestPath);
                                Handle handle = new Handle(controllerClass, declaredMethod);

                                //初始化 ActionMAP
                                REQUEST_HANDLE_MAP.put(request, handle);
                            }

                        }
                    }
                }
            }
        }
    }


    /**
     * 获取handle
     */
    public static Handle getHandle(String requestMethod, String requestPath) {
        Request request = new Request(requestMethod, requestPath);
        return REQUEST_HANDLE_MAP.get(request);

    }

}
