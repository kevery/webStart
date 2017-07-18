package com.kever.web.bean;

import java.util.Map;

public class Param {
    private Map<String, Object> paramsMap;

    public Param(Map<String, Object> paramsMap) {
        this.paramsMap = paramsMap;
    }

    public long getLong(String name) {
        return Long.valueOf(paramsMap.get(name).toString());
    }

    public Map<String, Object> getMap() {
        return paramsMap;
    }
}
