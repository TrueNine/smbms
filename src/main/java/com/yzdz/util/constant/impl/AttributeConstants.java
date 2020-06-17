package com.yzdz.util.constant.impl;

import com.yzdz.util.constant.StringConstantsInter;

/**
 * 用于存放前端地址
 *
 * @author TrueNine
 * @version 1.0
 * @date 2020/6/13
 */
public enum AttributeConstants implements StringConstantsInter {
    MESSAGE("message"),
    ERROR("error"),
    METHOD("method");

    String value;

    AttributeConstants(String v) {
        this.value = v;
    }

    @Override
    public String val() {
        return value;
    }
}
