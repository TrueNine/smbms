package com.yzdz.service.role;

import com.yzdz.pojo.Role;

import java.util.List;

/**
 * 角色表 Service 接口
 *
 * @author TrueNine
 * @version 1.0
 * @date 2020/6/16
 */
public interface RoleService {
    /**
     * 用于获取角色列表
     *
     * @return 角色列表
     */
    List<Role> getRoleList();
}
