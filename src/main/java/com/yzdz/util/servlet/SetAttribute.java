package com.yzdz.util.servlet;

import javax.servlet.http.HttpServletRequest;

/**
 * 用于设置Session提示的工具类
 *
 * @author TrueNine
 * @version 1.0
 * @date 2020/6/13
 */
public class SetAttribute {
    /**
     * 用于调用 HttpServletRequest 的 setAttribute
     *
     * @param req  HttpServletRequest
     * @param name setAttribute String 参数
     * @param o    setAttribute Object 参数
     */
    public static void setRequest(HttpServletRequest req, String name, Object o) {
        req.setAttribute(name, o);
    }
}