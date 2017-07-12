package com.kever.web.util;

import org.apache.commons.collections4.CollectionUtils;

import java.util.Collection;

public class CollectionUtil {
    public static boolean isEmpty(Collection<?> collection) {
        return CollectionUtils.isEmpty(collection);
    }


    public static boolean isNotEmpty(Collection<?> collection) {
        return !isEmpty(collection);
    }
}