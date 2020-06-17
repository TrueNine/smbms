package com.yzdz.filer;

import com.yzdz.pojo.User;
import com.yzdz.util.constant.impl.SessionConstants;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author TrueNine
 * @version 1.0
 * @date 2020/6/12
 */
public class SysFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        // 强转为 HttpServlet Req Resp
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        // 从Session 中获取用户对象
        Object u = req.getSession().getAttribute(SessionConstants.USER_SESSION.val());
        User user = u instanceof User ? (User) u : null;

        // 如果为 null,说明已经被移除
        if (null == user) {
            resp.sendRedirect(req.getContextPath() + "/error.jsp");
        } else {
            // 继续向下同行
            chain.doFilter(request, response);
        }
    }

    @Override
    public void destroy() {

    }
}
