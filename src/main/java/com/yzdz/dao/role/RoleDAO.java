package com.yzdz.dao.role;

import com.yzdz.pojo.Role;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * 角色表DAO接口
 *
 * @author TrueNine
 * @version 1.0
 * @date 2020/6/16
 */
public interface RoleDAO {
    /**
     * 用于获取角色列表
     *
     * @param connection 连接
     * @return 角色列表
     * @throws SQLException SQL异常
     */
    List<Role> getRoleList(Connection connection) throws SQLException;
}
