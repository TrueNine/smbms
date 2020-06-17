package com.yzdz.util.jdbc;

import com.yzdz.dao.BaseDAO;
import com.yzdz.util.constant.impl.EncodingConstants;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.util.Properties;

/**
 * 此类负责连接数据库,关闭资源等操作
 *
 * @author TrueNine
 * @version 1.0
 * @date 2020/6/15
 */
public class JDBCUtils {

    private static String driver;
    private static String url;
    private static String username;
    private static String password;

    /**
     * 数据库连接
     */
    private static Connection connection;

    static {
        // 读取配置文件
        Properties properties = new Properties();

        // 获得当前类加载器
        ClassLoader loader = BaseDAO.class.getClassLoader();
        InputStream is = loader.getResourceAsStream("db.properties");

        // 进行加载
        if (null != is) {
            try (InputStreamReader isr = new InputStreamReader(is, EncodingConstants.UTF_8.val())) {
                properties.load(isr);

                driver = properties.getProperty("driver");
                url = properties.getProperty("url");
                username = properties.getProperty("username");
                password = properties.getProperty("password");

            } catch (IOException e) {
                e.printStackTrace();
            }

            // 关闭流
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            // 连接数据库
            try {
                setConnection();
            } catch (ClassNotFoundException |
                    IllegalAccessException |
                    InstantiationException |
                    SQLException |
                    NoSuchMethodException |
                    InvocationTargetException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("JDBCUtils.static initializer" + "  配置文件加载为 null");
        }
    }

    /**
     * 私有化构造器
     * 这只是个工具类,禁止创建该对象
     *
     * @deprecated 不允许创建该对象
     */
    @Deprecated
    private JDBCUtils() {
    }

    /**
     * 用于连接数据库
     * 私有方法,仅供次工具类调用
     *
     * @throws ClassNotFoundException    找不到类
     * @throws IllegalAccessException    非法访问
     * @throws InstantiationException    实例化异常
     * @throws SQLException              数据库异常
     * @throws NoSuchMethodException     找不到方法
     * @throws InvocationTargetException 调用异常
     */
    private static void setConnection() throws
            ClassNotFoundException,
            IllegalAccessException,
            InstantiationException,
            SQLException,
            NoSuchMethodException,
            InvocationTargetException {
        Class<?> clazz = Class.forName(driver);
        Driver driver = (Driver) clazz.getConstructor().newInstance();

        Properties driverP = new Properties();
        driverP.setProperty("user", username);
        driverP.setProperty("password", password);

        connection = driver.connect(url, driverP);
    }

    /**
     * 用于获取数据库连接
     * 如果连接已经关闭则调用 setConnection 重新获取一个连接
     *
     * @return 返回连接
     */
    public static Connection getConnection() {
        try {
            if (connection.isClosed() || null == connection) {
                setConnection();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return connection;
    }

    /**
     * 用于关闭连接,不需要的参数直接传入 null 即可
     *
     * @param conn              连接
     * @param preparedStatement 预编译SQL
     * @param resultSet         结果集
     * @return 是否关闭成功
     */
    public static boolean freeSource(Connection conn, Statement preparedStatement, ResultSet resultSet) {
        boolean is = true;
        try {
            if (null != conn) {
                conn.close();
            }
            if (null != preparedStatement) {
                preparedStatement.close();
            }
            if (null != resultSet) {
                resultSet.close();
            }
        } catch (SQLException e) {
            is = false;
            e.printStackTrace();
        }
        return is;
    }
}