package com.yzdz.servlet.user;

import com.yzdz.util.constant.impl.SessionConstants;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 用户注销页面
 *
 * @author TrueNine
 * @version 1.0
 * @date 2020/6/12
 */
public class LogoutServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 移除用户 Session
        req.getSession().removeAttribute(SessionConstants.USER_SESSION.val());
        // 防止出错
        resp.sendRedirect(req.getContextPath() + "/login.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
