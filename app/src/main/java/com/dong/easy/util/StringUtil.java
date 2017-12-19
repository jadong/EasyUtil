package com.dong.easy.util;

/**
 * 🌑🌒🌓🌔🌕🌖🌗🌘
 * Created by zengwendong on 2017/12/19.
 */
public class StringUtil {

    public static String getSuffix(String string) {
        return getSuffix(string, "");
    }

    public static String getSuffix(String string, String defaultValue) {
        String suffix = defaultValue;
        int index = string.lastIndexOf(".");
        int index2 = string.lastIndexOf("?");
        if (index > -1) {
            if (index2 > -1) {
                suffix = string.substring(index, index2);
            } else {
                suffix = string.substring(index, string.length());
            }
        }
        return suffix;
    }

}
