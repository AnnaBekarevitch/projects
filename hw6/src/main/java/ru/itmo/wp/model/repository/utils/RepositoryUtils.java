package ru.itmo.wp.model.repository.utils;

import java.lang.reflect.Method;
import java.sql.ResultSet;

public class RepositoryUtils {
    public static String capitalize(String val){
        if(val.length() == 0){
            return val;
        }
        return val.substring(0, 1).toUpperCase() + val.substring(1);
    }

    public static Method getGetter(Class<?> token, String name) throws NoSuchMethodException {
        return token.getDeclaredMethod("get" + RepositoryUtils.capitalize(name));
    }

    public static Method getResultGetter(String name) throws NoSuchMethodException {
        return ResultSet.class.getDeclaredMethod("get" + RepositoryUtils.capitalize(name), int.class);
    }

    public static Method getSetter(Class<?> token, String name, Class<?> type) throws NoSuchMethodException {
        return token.getDeclaredMethod("set" + RepositoryUtils.capitalize(name), type);
    }
}
