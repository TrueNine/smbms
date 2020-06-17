package com.yzdz.service.role.impl;

import com.yzdz.dao.role.RoleDAO;
import com.yzdz.dao.role.impl.RoleDAOImpl;
import com.yzdz.pojo.Role;
import com.yzdz.service.role.RoleService;
import com.yzdz.util.jdbc.JDBCUtils;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * 角色表 service 实现类
 *
 * @author TrueNine
 * @version 1.0
 * @date 2020/6/16
 */
public class RoleServiceImpl implements RoleService {

    RoleDAO roleDAO;

    public RoleServiceImpl() {
        this.roleDAO = new RoleDAOImpl();
    }

    /**
     * 用于获取角色列表
     *
     * @return 角色列表
     */
    @Override
    public List<Role> getRoleList() {
        Connection connection = JDBCUtils.getConnection();
        try {
            return roleDAO.getRoleList(connection);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            JDBCUtils.freeSource(connection, null, null);
        }
        return null;
    }
}