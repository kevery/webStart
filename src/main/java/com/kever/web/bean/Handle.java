package com.kever.web.bean;

import java.lang.reflect.Method;

public class    Handle {
    private Class<?> controllerClass;

    private Method actionMethod;

    public Handle(Class<?> controllerClass, Method actionMethod) {
        this.controllerClass = controllerClass;
        this.actionMethod = actionMethod;
    }

    public Class<?> getControllerClass() {
        return controllerClass;
    }

    public Method getActionMethod() {
        return actionMethod;
    }
}
