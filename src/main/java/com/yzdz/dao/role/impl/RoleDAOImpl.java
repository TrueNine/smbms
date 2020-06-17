package com.yzdz.dao.role.impl;

import com.yzdz.dao.BaseDAO;
import com.yzdz.dao.role.RoleDAO;
import com.yzdz.pojo.Role;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * @author TrueNine
 * @version 1.0
 * @date 2020/6/16
 */
public class RoleDAOImpl extends BaseDAO implements RoleDAO {

    /**
     * 用于获取角色列表
     *
     * @param connection 连接
     * @return 角色列表
     * @throws SQLException SQL异常
     */
    @Override
    public List<Role> getRoleList(Connection connection) throws SQLException {
        String sql = "SELECT * FROM smbms_role;";
        List<Role> roles = null;
        if (null != connection) {
            try {
                roles = resultSetList(Role.class, select(connection, sql));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return roles;
    }
}
