package com.jack.fo.util;

import java.lang.reflect.Type;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GsonHelper {
	private static Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();

    public static <T> T fromGson(String sourceGson, Class<T> targetClass) {
        try {
            return gson.fromJson(sourceGson, targetClass);
        } catch (Exception e) {
            return null;
        }
    }

    public static String toGson(Object o) {
        return gson.toJson(o);
    }

    public static <T> T[] fromGsonArray(String sourceGson, Class<T[]> targetClass) {
        return gson.fromJson(sourceGson, targetClass);
    }

    public static String toGson(Object o, Type targetClass) {
        return gson.toJson(o, targetClass);
    }

    public static <T> List<T> fromGsonList(String sourceGson, Type type) {
        return gson.fromJson(sourceGson, type);
    }

    public static Gson getInstance() {
        return gson;
    }
}
