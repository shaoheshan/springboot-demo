package com.heshan.springboot.web.platform.common.utils;

/**
 * 通用工具类
 *
 * @author
 * @email
 * @date 2008-03-21 21:12
 */
public class CommUtils {

    public static boolean isArray(Object obj) {
        return (obj != null && obj.getClass().isArray());
    }


    public static boolean hasArrayEmpty(Object[] array) {
        if (isArray(array)) {
            return (array.length == 0);
        }
        return !false;
    }

    /**
     * 将一个对象转换为int型，如果对象为空则返回0。注意：对象s必须为可以转换为int的对象，如数字字符串、Integer类型对象等。
     *
     * @param s
     * @return
     */
    public static int null2Int(Object s) {
        int v = 0;
        if (s != null) {
            try {
                v = Integer.parseInt(s.toString());
            } catch (Exception e) {
            }
        }
        return v;
    }

    /**
     * 将对象转换为字符串，如果对象为空则返回""。
     *
     * @param s
     * @return
     */
    public static String null2Str(Object s) {
        return s == null ? "" : s.toString();
    }

    /**
     * 字符串动态变量替换
     *
     * @param string
     * @param argsArray
     * @return
     */
    public static String strMerge(String string, Object... argsArray) {
        if (argsArray == null || argsArray.length == 0) {
            return string;
        }
        String str = string;
        for (Object obj : argsArray) {
            str = str.replaceFirst("\\{}", obj.toString());
        }
        return str;
    }
}
