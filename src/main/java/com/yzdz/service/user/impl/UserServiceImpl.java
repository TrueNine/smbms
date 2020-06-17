package com.yzdz.service.user.impl;

import com.yzdz.dao.user.UserDAO;
import com.yzdz.dao.user.impl.UserDAOImpl;
import com.yzdz.pojo.User;
import com.yzdz.service.user.UserService;
import com.yzdz.util.jdbc.JDBCUtils;
import com.yzdz.util.str.StringUtils;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * 用户业务实现类
 *
 * @author TrueNine
 * @version 1.0
 * @date 2020/6/12
 */
public class UserServiceImpl implements UserService {

    private final UserDAO userDAO;

    /**
     * 构造器,初始化连接
     */
    public UserServiceImpl() {
        userDAO = new UserDAOImpl();
    }

    /**
     * 通过账户和密码登录
     *
     * @param userCode 账号
     * @param password 密码
     * @return 用户对象
     */
    @Override
    public User login(String userCode, String password) {
        Connection connection = JDBCUtils.getConnection();

        User loginUser = null;
        try {
            loginUser = userDAO.getLoginUser(connection, userCode, password);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.freeSource(connection, null, null);
        }
        return loginUser;
    }

    /**
     * 根据用户 id 修改用户密码
     *
     * @param id          用户 id
     * @param newPassword 新密码
     * @return 修改 是否成功
     */
    @Override
    public boolean modifyPassword(long id, String newPassword) {
        // 涉及事务,直接创建连接
        Connection conn = JDBCUtils.getConnection();

        boolean is = false;
        try {
            // 判断传入的值是否为空
            if (!StringUtils.isEmptyAll(newPassword, String.valueOf(id))) {
                // 密码修改成功
                is = userDAO.updatePassword(conn, id, newPassword) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.freeSource(conn, null, null);
        }
        return is;
    }

    /**
     * 根据 用户名或职位查询总数
     *
     * @param name     用户名
     * @param userRole 职位
     * @return 总数
     */
    @Override
    public long getUserCount(String name, int userRole) {
        Connection connection = JDBCUtils.getConnection();
        long userCount;
        try {
            userCount = userDAO.getUserCount(connection, name, userRole);
        } finally {
            JDBCUtils.freeSource(connection, null, null);
        }
        return userCount;
    }

    /**
     * 分页查询用户页码
     *
     * @param userName      用户名
     * @param userRole      职位id
     * @param currentPageNo 当前页码
     * @param pageSize      页面大小
     * @return 用户列表
     */
    @Override
    public List<User> getLimitUserList(String userName, long userRole, long currentPageNo, long pageSize) {
        Connection connection = JDBCUtils.getConnection();
        List<User> limitUserList = null;
        try {
            limitUserList = userDAO.getLimitUserList(connection, userName, userRole, currentPageNo, pageSize);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.freeSource(connection, null, null);
        }
        return limitUserList;
    }
}