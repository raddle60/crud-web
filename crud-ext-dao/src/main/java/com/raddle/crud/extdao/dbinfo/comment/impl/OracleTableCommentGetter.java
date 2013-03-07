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

public class OracleTableCommentGetter implements TableCommentGetter {

    private Connection connection;
    private Map<String, String> commentCache = new HashMap<String, String>();
    private static Logger logger = LoggerFactory.getLogger(OracleTableCommentGetter.class);

    public OracleTableCommentGetter(Connection connection){
        this.connection = connection;
    }

    @Override
    public String getColumnComment(String tableName, String columnName) {
        try {
            if (this.commentCache.containsKey(getCommentKey(tableName, columnName))) {
                return this.commentCache.get(getCommentKey(tableName, columnName));
            }

            Statement statement = this.connection.createStatement();
            ResultSet rs = statement.executeQuery("select * from user_col_comments where table_name = '" + tableName.toUpperCase() + "'");
            while (rs.next()) {
                String colName = rs.getString("COLUMN_NAME");
                this.commentCache.put(getCommentKey(tableName, colName), rs.getString("COMMENTS"));
            }
            rs.close();
            statement.close();
            return this.commentCache.get(getCommentKey(tableName, columnName));
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    @Override
    public String getTableComment(String tableName) {
        try {
            if (this.commentCache.containsKey(tableName.toUpperCase())) {
                return this.commentCache.get(tableName.toUpperCase());
            }

            Statement statement = this.connection.createStatement();
            ResultSet rs = statement.executeQuery("select * from user_tab_comments where table_name = '" + tableName.toUpperCase() + "'");
            while (rs.next()) {
                this.commentCache.put(tableName.toUpperCase(), rs.getString("COMMENTS"));
            }

            rs.close();
            statement.close();
            return this.commentCache.get(tableName.toUpperCase());
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    private String getCommentKey(String tableName, String columnName) {
        return tableName.toUpperCase() + "-" + columnName.toUpperCase();
    }
}
