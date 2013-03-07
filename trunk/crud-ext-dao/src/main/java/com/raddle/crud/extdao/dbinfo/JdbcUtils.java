package com.raddle.crud.extdao.dbinfo;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * 类JdbcUtils.java的实现描述：操作jdbc
 *
 * @author xurong 2009-6-26 下午02:45:10
 */
public class JdbcUtils {

    /**
     * 关闭连接
     *
     * @param conn
     */
    public static void close(Connection conn) {
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Can not close connection.", e);
        }
    }

    /**
     * 关闭查询
     *
     * @param statement
     */
    public static void close(Statement statement) {
        try {
            if (statement != null) {
                statement.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Can not close statement.", e);
        }
    }

    /**
     * 关闭结果
     *
     * @param resultSet
     */
    public static void close(ResultSet resultSet) {
        try {
            if (resultSet != null) {
                resultSet.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Can not close resultSet.", e);
        }
    }
}
