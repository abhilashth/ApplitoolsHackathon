package com.applitools.hackathon.utils;


import org.apache.commons.beanutils.BeanUtils;

import java.util.HashMap;
import java.util.Map;

public class ObjectConverter {

    public static <T> Map<String, String> convertObjectToMap(T t) {
        Map<String, String> map = new HashMap<>();
        try {
            Map<String, String> tempMap = BeanUtils.describe(t);
            tempMap.entrySet().stream().filter(entry -> !entry.getKey().equals("class")).forEach(entry -> map.put(entry.getKey(),
                    String.valueOf(entry.getValue())));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return map;
    }

    public static <T> Map<String, String> objectToMap(T t) {
        Map<String, String> tempMap = null;
        try {
            tempMap = BeanUtils.describe(t);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return tempMap;
    }

}
