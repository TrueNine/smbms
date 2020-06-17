package com.yzdz.util.str;

/**
 * 一个工具类,用于处理字符串的一些方法
 *
 * @author TrueNine
 * @version 1.0
 * @date 2020/6/13
 */
public class StringUtils {

    /**
     * 判断传入的字符串是否为空
     *
     * @param str 字符串
     * @return 为 null 或者 为 "" 则返回 true,反之则 false
     */
    public static boolean isEmpty(String str) {
        return null == str || "".equals(str);
    }

    /**
     * 用于判断一整组字符串是否为空
     *
     * @param strings 字符串
     * @return 只要其中一个为 null 或者 "",则直接返回 true,反之则 false
     */
    public static boolean isEmptyAll(String... strings) {
        for (String temp : strings) {
            if (isEmpty(temp)) {
                return true;
            }
        }
        return false;
    }
}
