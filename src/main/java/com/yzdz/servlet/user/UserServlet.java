package com.yzdz.servlet.user;

import com.alibaba.fastjson.JSONArray;
import com.yzdz.pojo.Role;
import com.yzdz.pojo.User;
import com.yzdz.service.role.RoleService;
import com.yzdz.service.role.impl.RoleServiceImpl;
import com.yzdz.service.user.UserService;
import com.yzdz.service.user.impl.UserServiceImpl;
import com.yzdz.util.constant.impl.AttributeConstants;
import com.yzdz.util.constant.impl.ParameterConstants;
import com.yzdz.util.constant.impl.SessionConstants;
import com.yzdz.util.jdbc.LimitUtil;
import com.yzdz.util.servlet.SetAttribute;
import com.yzdz.util.str.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 修改密码 Servlet层
 *
 * @author TrueNine
 * @version 1.0
 * @date 2020/6/13
 */
public class UserServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("modifyPassword.doGet");
        String method = req.getParameter(AttributeConstants.METHOD.val());

        // 判断是否是执行那种方法
        if (!StringUtils.isEmpty(method)) {
            if (method.equals(ParameterConstants.SAVE_PASSWORD.val()))
                rePassword(req, resp);
            else if (method.equals(ParameterConstants.PASSWORD_MODIFY.val()))
                showOldPassword(req, resp);
            else if (method.equals(ParameterConstants.QUERY.val()))
                query(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("modifyPassword.doPost");
        doGet(req, resp);
    }

    /**
     * 用于修改密码的方法
     * 用于servlet调用
     *
     * @param req  请求
     * @param resp 相应
     * @throws ServletException servlet 异常
     * @throws IOException      IO异常
     */
    private void rePassword(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("modifyPassword.rePassword");
        // 获取用户信息
        HttpSession session = req.getSession();
        Object u = session.getAttribute(SessionConstants.USER_SESSION.val());

        // 进行修改密码
        boolean is = false;
        if (null != u) {
            String newPassword = req.getParameter(ParameterConstants.NEW_PASSWORD.val());

            System.out.println("newPassword = " + newPassword);
            if (!StringUtils.isEmpty(newPassword)) {
                User user = u instanceof User ? (User) u : null;
                if (null != user) {
                    UserService userService = new UserServiceImpl();
                    is = userService.modifyPassword(user.getId(), newPassword);
                }
            } else {
                SetAttribute.setRequest(req, AttributeConstants.MESSAGE.val(),
                        "修改密码不能为空");
            }
        } else {
            // 提示当前用户不存在
            SetAttribute.setRequest(req, AttributeConstants.MESSAGE.val(),
                    "当前用户不存在");
        }

        // 提示前端
        if (is) {
            // 移出 Session
            SetAttribute.setRequest(req, AttributeConstants.MESSAGE.val(),
                    "修改成功,请重新登录");
            session.removeAttribute(SessionConstants.USER_SESSION.val());
        } else {
            SetAttribute.setRequest(req, AttributeConstants.METHOD.val(),
                    "密码修改失败");
        }
        req.getRequestDispatcher("pwdmodify.jsp").forward(req, resp);
    }

    /**
     * 使用 ajax 验证旧密码
     *
     * @param req  请求
     * @param resp 相应
     * @throws ServletException servlet异常
     * @throws IOException      IO异常
     */
    private void showOldPassword(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("modifyPassword.showOldPassword");
        HttpSession session = req.getSession();
        String oldPassword = req.getParameter(ParameterConstants.OLD_PASSWORD.val());

        Object u = session.getAttribute(SessionConstants.USER_SESSION.val());
        Map<String, Object> resultMap = new HashMap<>();
        if (null == u) {
            resultMap.put("result", "sessionerror");
        } else if (StringUtils.isEmpty(oldPassword)) {
            resultMap.put("result", "error");
        } else {
            String userPassword = ((User) u).getUserPassword();
            if (oldPassword.equals(userPassword)) {
                resultMap.put("result", "true");
            } else {
                resultMap.put("result", "false");
            }
        }
        resp.setContentType("application/json");
        PrintWriter pw = resp.getWriter();
        // 使用工具类,将 JSON传递给前端
        pw.write(JSONArray.toJSONString(resultMap));

        pw.flush();
        pw.close();
    }

    private void query(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        // 查询用户列表

        // 从前端获取数据
        String queryUserName = req.getParameter(ParameterConstants.QUERY_NAME.val());
        String temp = req.getParameter(ParameterConstants.QUERY_USER_ROLE.val());
        String pageIndex = req.getParameter(ParameterConstants.PAGE_INDEX.val());
        int queryUserRole = 0;

        // 建议此处写入配置文件
        int pageSize = 5;
        long currentPageNu = 1;

        // 为姓名作非空操作
        if (StringUtils.isEmpty(queryUserName)) {
            queryUserName = "";
        }
        // 为角色赋值
        if (!StringUtils.isEmpty(temp)) {
            queryUserRole = Integer.parseInt(temp);
        }
        // 解析页码
        if (!StringUtils.isEmpty(pageIndex)) {
            try {
                currentPageNu = Integer.parseInt(pageIndex);
            } catch (NumberFormatException e) {
                resp.sendRedirect("error.jsp");
            }
        }

        // 获取用户总数
        UserService userService = new UserServiceImpl();
        RoleService roleService = new RoleServiceImpl();
        long totalCount = userService.getUserCount(queryUserName, queryUserRole);

        // 使用分页工具类设置
        LimitUtil limitUtil = new LimitUtil();
        limitUtil.setCurrentPageNo(currentPageNu);
        limitUtil.setPageSize(pageSize);
        limitUtil.setTotalPageCount(totalCount);
        // 总页数
        long totalPageCount = limitUtil.getTotalPageCount();

        // 控制首页和尾页
        if (currentPageNu < 1) {
            currentPageNu = 1;
        } else if (currentPageNu > totalPageCount) {
            currentPageNu = totalPageCount;
        }

        // 获取用户列表
        List<User> userList = userService.getLimitUserList(queryUserName, queryUserRole, currentPageNu, pageSize);
        List<Role> roleList = roleService.getRoleList();

        // 设置显示
        SetAttribute.setRequest(req, "userList", userList);
        SetAttribute.setRequest(req, "roleList", roleList);

        // 显示前端
        req.getRequestDispatcher("userlist.jsp").forward(req, resp);

    }
}