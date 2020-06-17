package com.yzdz.util.constant.impl;

import com.yzdz.util.constant.StringConstantsInter;

/**
 * @author TrueNine
 * @version 1.0
 * @date 2020/6/12
 */
public enum SessionConstants implements StringConstantsInter {
    USER_SESSION("userSession");

    private String type;

    SessionConstants(String str) {
        this.type = str;
    }

    @Override
    public String val() {
        return this.type;
    }
}
