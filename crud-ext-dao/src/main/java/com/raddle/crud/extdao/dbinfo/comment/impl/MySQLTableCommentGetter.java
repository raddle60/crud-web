package com.raddle.crud.extdao.dbinfo.comment.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.raddle.crud.extdao.dbinfo.comment.TableCommentGetter;

public class MySQLTableCommentGetter implements TableCommentGetter {

    private Connection connection;
    private Map<String, String> commentCache = new HashMap<String, String>();
    private static Logger logger = LoggerFactory.getLogger(OracleTableCommentGetter.class);

    public MySQLTableCommentGetter(Connection connection){
        this.connection = connection;
    }

    @Override
    public String getColumnComment(String tableName, String columnName) {
        return "";
    }

    @Override
    public String getTableComment(String tableName) {
        try {
            if (this.commentCache.containsKey(tableName)) {
                return this.commentCache.get(tableName);
            }

            Statement statement = this.connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT t.TABLE_NAME,t.TABLE_COMMENT FROM INFORMATION_SCHEMA.TABLES t  WHERE table_schema = '" + this.connection.getCatalog() + "'");
            while (rs.next()) {
                String tName = rs.getString("TABLE_NAME");
                String fullComment = rs.getString("TABLE_COMMENT");
                int innoDbIndex = fullComment.indexOf("InnoDB");
                this.commentCache.put(tName, fullComment.substring(0, (innoDbIndex != -1) ? innoDbIndex : fullComment.length()));
            }
            rs.close();
            statement.close();
            return this.commentCache.get(tableName);
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    public Connection getConnection() {
        return this.connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }
}
