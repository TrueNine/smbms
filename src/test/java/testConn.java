import com.yzdz.dao.role.RoleDAO;
import com.yzdz.dao.role.impl.RoleDAOImpl;
import com.yzdz.dao.user.UserDAO;
import com.yzdz.dao.user.impl.UserDAOImpl;
import com.yzdz.pojo.Role;
import com.yzdz.pojo.User;
import com.yzdz.service.role.RoleService;
import com.yzdz.service.role.impl.RoleServiceImpl;
import com.yzdz.service.user.UserService;
import com.yzdz.service.user.impl.UserServiceImpl;
import com.yzdz.util.constant.impl.EncodingConstants;
import com.yzdz.util.jdbc.JDBCUtils;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * @author TrueNine
 * @version 1.0
 * @date 2020/6/11
 */
public class testConn {

    /**
     * 测试连接数据库
     * 测试通过
     */
    @Test
    public void testConnection() {
        Connection connection = JDBCUtils.getConnection();
        System.out.println(connection);
    }

    /**
     * 测试常量枚举类
     * 测试通过
     */
    @Test
    public void testConstants() {
        String val = EncodingConstants.UTF_8.val();
        System.out.println(val);
    }

    /**
     * 用户登录查询
     * 测试通过
     */
    @Test
    public void testQueryUser() {
        UserDAO u = new UserDAOImpl();
        try {
            User liming = u.getLoginUser(
                    JDBCUtils.getConnection(),
                    "admin", "123456"
            );
            System.out.println(liming);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * 测试UserServiceLogin
     * 测试通过
     */
    @Test
    public void testServiceLoginUser() {
        UserService u = new UserServiceImpl();
        User admin = u.login("admin", "1234567");
        System.out.println(admin);
    }

    /**
     * 测试 查询用户总数
     * 测试通过
     */
    @Test
    public void testUserCount() {
        UserService u = new UserServiceImpl();
        long userCount = u.getUserCount(null, 3);
        System.out.println("用户总数为: " + userCount);
    }

    /**
     * 测试分页 DAO层
     * 测试通过
     */
    @Test
    public void testLimitDAO() throws SQLException {
        UserDAO u = new UserDAOImpl();
        List<User> limitUserList = u.getLimitUserList(
                JDBCUtils.getConnection(),
                "系统",
                0,
                1,
                10
        );
        System.out.println(limitUserList.toString());
    }

    /**
     * 测试service层分页
     * 测试通过
     */
    @Test
    public void testLimitService() {
        UserService u = new UserServiceImpl();
        List<User> limitUserList = u.getLimitUserList(
                "系统",
                0,
                1,
                10
        );
        System.out.println(limitUserList.toString());
        System.out.println(limitUserList.size());
    }

    /**
     * 测试查询Role列表
     * 测试通过
     */
    @Test
    public void testGetRole() throws SQLException {
        RoleDAO u = new RoleDAOImpl();
        List<Role> roleList = u.getRoleList(JDBCUtils.getConnection());
        System.out.println(roleList);
        System.out.println(roleList.size());
    }

    /**
     * 测试Role Service
     * 测试通过
     */
    @Test
    public void testRoleService() {
        RoleService u = new RoleServiceImpl();
        List<Role> roleList = u.getRoleList();
        System.out.println(roleList);
        System.out.println(roleList.size());
    }
}