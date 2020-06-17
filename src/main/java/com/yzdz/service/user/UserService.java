package com.yzdz.service.user;

import com.yzdz.pojo.User;

import java.util.List;

/**
 * @author TrueNine
 * @version 1.0
 * @date 2020/6/12
 */
public interface UserService {
    /**
     * 通过账户和密码登录
     *
     * @param userCode 账号
     * @param password 密码
     * @return 用户对象
     */
    User login(String userCode, String password);

    /**
     * 根据用户 id 修改用户密码
     *
     * @param id          用户 id
     * @param newPassword 新密码
     * @return 修改 是否成功
     */
    boolean modifyPassword(long id, String newPassword);

    /**
     * 根据 用户名或职位查询总数
     *
     * @param name     用户名
     * @param userRole 职位
     * @return 总数
     */
    long getUserCount(String name, int userRole);

    /**
     * 分页查询用户页码
     *
     * @param userName      用户名
     * @param userRole      职位id
     * @param currentPageNo 当前页码
     * @param pageSize      页面大小
     * @return 用户列表
     */
    List<User> getLimitUserList(String userName, long userRole, long currentPageNo, long pageSize);
}