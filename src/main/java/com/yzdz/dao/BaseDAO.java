package com.yzdz.dao;

import com.yzdz.util.jdbc.JDBCUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;

/**
 * 提供基本的数据库操作,与任何表不相关
 * 任何表操作可以继承此类,以此节省重复代码
 *
 * @author TrueNine
 * @version 1.0
 * @date 2020/6/11
 */
public abstract class BaseDAO {

    /**
     * 私有方法,用于设置一句SQL,并返回设置好的预编译SQL(PreparedStatement)
     *
     * @param conn 连接
     * @param sql  SQL语句
     * @param args 参数列表
     * @return 设置好参数的 PreparedStatement
     * @throws SQLException SQL 异常
     */
    private PreparedStatement setPreparedStatementArgs(Connection conn, String sql, Object... args) throws SQLException {
        PreparedStatement p = conn.prepareStatement(sql);
        for (int i = 0; i < args.length; i++) {
            p.setObject(i + 1, args[i]);
        }
        return p;
    }

    /**
     * 用于查询的公共基方法
     *
     * @param conn 连接
     * @param sql  SQL语句
     * @param args 参数列表
     * @return 结果集
     */
    public ResultSet select(Connection conn, String sql, Object... args) throws SQLException {
        PreparedStatement p = setPreparedStatementArgs(conn, sql, args);
        return p.executeQuery();
    }

    /**
     * 修改方法,专门用于DML
     *
     * @param conn 连接
     * @param sql  SQL语句
     * @param args 参数列表
     * @return 修改的数据的行数
     * @throws SQLException SQL异常
     */
    public int update(Connection conn, String sql, Object... args) throws SQLException {
        PreparedStatement p = setPreparedStatementArgs(conn, sql, args);
        int executeUpdate = p.executeUpdate();
        JDBCUtils.freeSource(null, p, null);
        return executeUpdate;
    }

    /**
     * 用于处理数据库  函数查询的结果集
     * 比如: COUNT(1)...
     * <h1>切勿将此方法用于别的查询</h1>
     *
     * @param elementType 返回类型
     * @param connection  连接
     * @param sql         SQL函数
     * @param args        参数列表
     * @param <E>         返回类型
     * @return 对应 返回类型 的 结果
     */
    @SuppressWarnings("all")
    public <E> E selectFunction(Class<? extends E> elementType, ResultSet resultSet) throws SQLException {
        E value = resultSet.next() ? (E) resultSet.getObject(1) : null;
        JDBCUtils.freeSource(null, null, resultSet);
        return value;
    }

    /**
     * 用于处理只有一条ORM的结果集,(只会返回一条!!!)
     * 类型只支持ORM映射对象,内部使用反射逐一设置值
     *
     * @param ORMType   映射对象类型
     * @param resultSet 结果集
     * @param <T>       类 类型
     * @return ORM
     * @throws SQLException              数据库异常
     * @throws NoSuchMethodException     没有方法异常
     * @throws IllegalAccessException    非法访问异常
     * @throws InvocationTargetException 调用异常
     * @throws InstantiationException    实例化异常
     * @throws NoSuchFieldException      找不到字段异常
     */
    public <T> T resultSetToORMOneLine(Class<? extends T> ORMType, ResultSet resultSet) throws
            SQLException,
            NoSuchMethodException,
            IllegalAccessException,
            InvocationTargetException,
            InstantiationException,
            NoSuchFieldException {
        if (resultSet.next()) {
            T result = ORMType.getConstructor().newInstance();

            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();
            for (int i = 0; i < columnCount; i++) {
                Object value = resultSet.getObject(i + 1);
                Field field = ORMType.getDeclaredField(
                        metaData.getColumnLabel(i + 1)
                );
                field.setAccessible(true);
                field.set(result, value);
            }
            JDBCUtils.freeSource(null, null, resultSet);
            return result;
        }
        return null;
    }

    /**
     * 用于处理结果集
     * 返回一个 List(内部为 LinkedList)
     * 内部使用反射,别名设置属性
     *
     * @param ORMType   映射对象的 类 类型
     * @param resultSet 结果集
     * @param <T>       泛型
     * @return List LinkedList
     * @throws SQLException              数据库异常
     * @throws NoSuchMethodException     无方法异常
     * @throws IllegalAccessException    非法访问异常
     * @throws InvocationTargetException 调用异常
     * @throws InstantiationException    实例化异常
     * @throws NoSuchFieldException      无属性异常
     */
    public <T> List<T> resultSetList(Class<? extends T> ORMType, ResultSet resultSet) throws
            SQLException,
            NoSuchMethodException,
            IllegalAccessException,
            InvocationTargetException,
            InstantiationException,
            NoSuchFieldException {
        List<T> result;

        result = new LinkedList<>();
        ResultSetMetaData metaData = resultSet.getMetaData();
        // 获取列数
        int columnCount = metaData.getColumnCount();
        while (resultSet.next()) {
            T orm = ORMType.getConstructor().newInstance();
            for (int i = 0; i < columnCount; i++) {
                Object value = resultSet.getObject(i + 1);
                // 获得列别名的属性
                Field field = orm.getClass().getDeclaredField(
                        metaData.getColumnLabel(i + 1)
                );
                // 进行设置
                field.setAccessible(true);
                field.set(orm, value);
            }
            result.add(orm);
        }
        JDBCUtils.freeSource(null, null, resultSet);
        return result;
    }
}