package com.yzdz.dao.user;

import com.yzdz.pojo.User;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * 用户的增删改查
 *
 * @author TrueNine
 * @version 1.0
 * @date 2020/6/11
 */
public interface UserDAO {
    /**
     * 通过 userCode 来验证登录
     * 不存在则直接返回 null
     *
     * @param connection   连接
     * @param userCode     userCode
     * @param userPassword userPassword
     * @return User 映射对象
     * @throws SQLException SQL异常
     */
    User getLoginUser(Connection connection, String userCode, String userPassword) throws SQLException;

    /**
     * 用于修改用户密码
     *
     * @param connection   连接
     * @param id           用户id
     * @param userPassword 账户密码
     * @return 数值
     * @throws SQLException SQL异常
     */
    int updatePassword(Connection connection, long id, String userPassword) throws SQLException;

    /**
     * 根据用户名或角色,查询用户总数
     *
     * @param connection 连接
     * @param userName   用户名
     * @param userRole   用户角色
     * @return 总数
     */
    long getUserCount(Connection connection, String userName, int userRole);

    /**
     * 用户分页
     *
     * @param connection    连接
     * @param userName      用户名
     * @param userRole      职位
     * @param currentPageNo 当前页码
     * @param pageSize      页面大小
     * @return 用户列表
     * @throws SQLException SQL异常
     */
    List<User> getLimitUserList(Connection connection, String userName, long userRole, long currentPageNo, long pageSize) throws SQLException;
}