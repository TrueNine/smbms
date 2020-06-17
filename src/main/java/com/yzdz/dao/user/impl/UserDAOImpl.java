package com.yzdz.dao.user.impl;

import com.yzdz.dao.BaseDAO;
import com.yzdz.dao.user.UserDAO;
import com.yzdz.pojo.User;
import com.yzdz.util.jdbc.JDBCUtils;
import com.yzdz.util.str.StringUtils;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * 用户查询接口实现类
 *
 * @author TrueNine
 * @version 1.0
 * @date 2020/6/11
 */
public class UserDAOImpl extends BaseDAO implements UserDAO {

    /**
     * 通过 userCode 来验证登录
     * 不存在则直接返回 null
     *
     * @param connection   连接
     * @param userCode     userCode
     * @param userPassword userPassword
     * @return User 映射对象
     */
    @Override
    public User getLoginUser(Connection connection, String userCode, String userPassword) {
        if (!StringUtils.isEmptyAll(userCode, userPassword)) {
            String sql = "select * from smbms_user where userCode = ? and userPassword = ?;";
            try {
                return resultSetToORMOneLine(User.class,
                        select(connection, sql, userCode, userPassword)
                );
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 用于修改用户密码
     *
     * @param connection      连接
     * @param id              用户id
     * @param newUserPassword 账户密码
     * @return 数值
     * @throws SQLException SQL异常
     */
    @Override
    public int updatePassword(Connection connection, long id, String newUserPassword) throws SQLException {
        int update = 0;
        if (null != connection) {
            String sql = "update smbms_user set userPassword = ? where id = ?;";
            update = StringUtils.isEmptyAll(String.valueOf(id), newUserPassword)
                    ? 0 : update(connection, sql, newUserPassword, id);
        }
        return update;
    }

    /**
     * 根据用户名或角色,查询用户总数
     *
     * @param connection 连接
     * @param userName   用户名
     * @param userRole   用户角色
     * @return 总数
     */
    @Override
    public long getUserCount(Connection connection, String userName, int userRole) {
        Long resultSetOneLine = null;
        if (null != connection) {
            StringBuffer sqlBuffer = new StringBuffer();
            List<Object> argsList = new ArrayList<>();

            sqlBuffer.append("SELECT COUNT(1) AS c\n" +
                    "FROM smbms_user AS `u`,\n" +
                    "     smbms_role AS `r`\n" +
                    "WHERE r.id = u.userRole");

            // 进行 SQL,参数 拼接
            // 填写第一个参数
            if (!StringUtils.isEmpty(userName)) {
                sqlBuffer.append(" AND u.userName LIKE ?");
                argsList.add("%" + userName + "%");
            }
            // 填写第二个参数
            if (userRole > 0) {
                sqlBuffer.append(" AND r.id = ?");
                argsList.add(userRole);
            }
            sqlBuffer.append(';');

            System.out.println("UserDAOImpl.getUserCount");
            System.out.println("SQL = " + sqlBuffer.toString());

            // 执行SQL,获得参数
            resultSetOneLine = null;
            try {
                resultSetOneLine = selectFunction(Long.class,
                        select(connection, sqlBuffer.toString(), argsList.toArray())
                );
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                JDBCUtils.freeSource(connection, null, null);
            }
        }
        return null == resultSetOneLine ? 0 : resultSetOneLine;
    }

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
    @Override
    public List<User> getLimitUserList(Connection connection, String userName, long userRole, long currentPageNo, long pageSize) throws SQLException {
        if (connection != null) {
            StringBuffer sqlBuffer = new StringBuffer();
            List<Object> argsList = new LinkedList<>();
            sqlBuffer.append("SELECT u.*\n" +
                    "FROM smbms_user AS `u`,\n" +
                    "     smbms_role AS `r`\n" +
                    "WHERE r.id = u.userRole");
            // 填充参数和SQL
            if (!StringUtils.isEmpty(userName)) {
                sqlBuffer.append("\n AND u.userName LIKE ?");
                argsList.add("%" + userName + "%");
            }
            if (userRole > 0) {
                sqlBuffer.append(" AND r.id = ?");
                argsList.add(userRole);
            }
            sqlBuffer.append(" ORDER BY u.id DESC\n" +
                    " LIMIT ?,?;");

            System.out.println("UserDAOImpl.getUserList");
            System.out.println(sqlBuffer.toString());

            // 计算limit 第一个参数
            // 第一页: (1,10) == <0,10>
            // 第二页: (2,10) == <10,10>
            currentPageNo = (currentPageNo - 1) * pageSize;
            // 添加当前页码参数
            argsList.add(currentPageNo);
            argsList.add(pageSize);

            // 进行查询
            try {
                // 如果没有异常直接返回列表
                return resultSetList(User.class,
                        select(connection,
                                sqlBuffer.toString(),
                                argsList.toArray()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}