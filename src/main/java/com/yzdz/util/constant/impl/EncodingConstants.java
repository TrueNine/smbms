package com.yzdz.util.constant.impl;

import com.yzdz.util.constant.StringConstantsInter;

/**
 * 一部分字符编码的常量
 *
 * @author TrueNine
 * @version 1.0
 * @date 2020/6/11
 */
public enum EncodingConstants implements StringConstantsInter {
    UTF_8("UTF-8"),
    UTF_16("UTF-16"),
    UTF_32("UTF-32"),
    GBK("GBK"),
    GB2312("GB2312"),
    ISO_8859_1("ISO-8859-1"),
    @Deprecated
    UNICODE("unicode");

    private final String type;

    EncodingConstants(String type) {
        this.type = type;
    }

    @Override
    public String val() {
        return type;
    }
}