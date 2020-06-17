package com.yzdz.util.constant.impl;

import com.yzdz.util.constant.StringConstantsInter;

/**
 * 用于存放 请求参数的值
 *
 * @author TrueNine
 * @version 1.0
 * @date 2020/6/13
 */
public enum ParameterConstants implements StringConstantsInter {
    NEW_PASSWORD("newpassword"),
    USER_CODE("userCode"),
    USER_PASSWORD("userPassword"),
    SAVE_PASSWORD("savepwd"),
    PASSWORD_MODIFY("pwdmodify"),
    OLD_PASSWORD("oldpassword"),
    QUERY("query"),
    QUERY_NAME("queryname"),
    QUERY_USER_ROLE("queryUserRole"),
    PAGE_INDEX("pageIndex");

    String value;

    ParameterConstants(String v) {
        this.value = v;
    }

    @Override
    public String val() {
        return this.value;
    }
}
