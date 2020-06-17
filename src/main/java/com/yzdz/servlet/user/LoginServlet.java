package com.yzdz.servlet.user;

import com.yzdz.pojo.User;
import com.yzdz.service.user.UserService;
import com.yzdz.service.user.impl.UserServiceImpl;
import com.yzdz.util.constant.impl.AttributeConstants;
import com.yzdz.util.constant.impl.ParameterConstants;
import com.yzdz.util.constant.impl.SessionConstants;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 用户登录Servlet
 *
 * @author TrueNine
 * @version 1.0
 * @date 2020/6/12
 */
public class LoginServlet extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("LoginServlet.doGet");

        // 获取用户名和密码
        String userCode = req.getParameter(ParameterConstants.USER_CODE.val());
        String userPassword = req.getParameter(ParameterConstants.USER_PASSWORD.val());

        // 判断用户是否输入完整
        if ((null != userCode && !"".equals(userCode))
                && (null != userPassword && !"".equals(userPassword))
        ) {
            // 和数据库中的数据进行对比
            UserService userService = new UserServiceImpl();
            User login = userService.login(userCode, userPassword);

            // 拥有此用户,可以进行登录
            if (null != login) {
                // 将用户信息放入 session
                req.getSession().setAttribute(SessionConstants.USER_SESSION.val(), login);
                // 跳转到内部主页
                resp.sendRedirect("jsp/frame.jsp");
            } else {
                // 转发回登录页面,顺带提示
                req.setAttribute(AttributeConstants.ERROR.val(), "用户名或者密码不正确");
                req.getRequestDispatcher("login.jsp").forward(req, resp);
            }
        } else {
            // 向用户提示为空
            req.setAttribute(AttributeConstants.ERROR.val(), "不能有项目输入为空");
            req.getRequestDispatcher("login.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
