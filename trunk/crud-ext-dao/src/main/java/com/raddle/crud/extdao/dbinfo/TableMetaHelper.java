package com.raddle.crud.extdao.dbinfo;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.raddle.crud.extdao.dbinfo.comment.TableCommentGetter;
import com.raddle.crud.extdao.dbinfo.comment.impl.DefaultTableCommentGetter;
import com.raddle.crud.extdao.dbinfo.comment.impl.MySQLTableCommentGetter;
import com.raddle.crud.extdao.dbinfo.comment.impl.OracleTableCommentGetter;
import com.raddle.crud.extdao.dbinfo.model.ColumnInfo;
import com.raddle.crud.extdao.dbinfo.model.TableInfo;

/**
 * 类TableMetaHelper.java的实现描述：获取table信息
 *
 * @author xurong 2009-6-26 下午03:56:43
 */
public class TableMetaHelper {

    private static Logger logger = LoggerFactory.getLogger(TableMetaHelper.class);

    private Connection connection;

    /**
     * 通过jdbc查询数据，用完后不关闭连接
     * @param connection
     */
    public TableMetaHelper(Connection connection){
        this.connection = connection;
    }

    /**
     * 获取表结构信息
     * @param tableNames
     * @param schema
     * @param types Typical types are "TABLE", "VIEW", "SYSTEM TABLE", "GLOBAL TEMPORARY", "LOCAL TEMPORARY", "ALIAS", "SYNONYM".
     * @return
     */
    public List<TableInfo> getTableInfo(String[] tableNames, String schema, String[] types) {
        List<TableInfo> tableInfoList = new ArrayList<TableInfo>();
        logger.info("Start getting information of database ...");
        try {
            if (types == null) {
                types = new String[] { "TABLE" };
            }
            DatabaseMetaData dbMeta = connection.getMetaData();
            if (tableNames == null) {
                ResultSet rs = connection.getMetaData().getTables(null, schema, "%", types);
                fillTableInfo(tableInfoList, schema, dbMeta, rs);
                JdbcUtils.close(rs);
            } else {
                for (String tableName : tableNames) {
                    ResultSet rs = connection.getMetaData().getTables(null, schema, tableName, types);
                    fillTableInfo(tableInfoList, schema, dbMeta, rs);
                    JdbcUtils.close(rs);
                }
            }
            logger.info("Complete getting information of database.");
            Collections.sort(tableInfoList, new Comparator<TableInfo>() {

                @Override
                public int compare(TableInfo o1, TableInfo o2) {
                    if (o1 == null || o1.getTableName() == null) {
                        return -1;
                    }
                    if (o2 == null) {
                        return 1;
                    }
                    return o1.getTableName().compareTo(o2.getTableName());
                }
            });
            return tableInfoList;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    private void fillTableInfo(List<TableInfo> tableInfoList, String schema, DatabaseMetaData dbMeta, ResultSet rs) throws SQLException {
        while (rs.next()) {
            TableInfo tableInfo = new TableInfo();
            tableInfo.setTableName(rs.getString("TABLE_NAME"));
            logger.info("Getting information for table [{}] .", tableInfo.getTableName());
            TableCommentGetter commentGetter = getTableCommentGetter(dbMeta.getConnection());
            logger.debug("Getting {}'s comment .", tableInfo.getTableName());
            tableInfo.setComment((StringUtils.isBlank(rs.getString("REMARKS"))) ? commentGetter.getTableComment(tableInfo.getTableName()) : rs.getString("REMARKS"));
            logger.debug("Getting {}'s columns .", tableInfo.getTableName());
            ResultSet colRet = dbMeta.getColumns(null, schema, tableInfo.getTableName(), "%");
            logger.debug("Getting {}'s primaryKeys .", tableInfo.getTableName());
            ResultSet pkRet = dbMeta.getPrimaryKeys(null, schema, tableInfo.getTableName());
            Map<String, Object> primaryKeyMap = new HashMap<String, Object>();
            while (pkRet.next()) {
                primaryKeyMap.put(pkRet.getString("COLUMN_NAME"), null);
            }

            while (colRet.next()) {
                ColumnInfo columnInfo = new ColumnInfo();
                columnInfo.setTableName(tableInfo.getTableName());
                columnInfo.setColumnName(colRet.getString("COLUMN_NAME"));
                columnInfo.setColumnType(colRet.getInt("DATA_TYPE"));
                columnInfo.setColumnTypeName(colRet.getString("TYPE_NAME"));
                columnInfo.setComment((StringUtils.isBlank(colRet.getString("REMARKS"))) ? commentGetter.getColumnComment(tableInfo.getTableName(), columnInfo.getColumnName()) : colRet.getString("REMARKS"));
                columnInfo.setLength(colRet.getInt("COLUMN_SIZE"));
                columnInfo.setPrecision(columnInfo.getLength());
                columnInfo.setScale(colRet.getInt("DECIMAL_DIGITS"));
                columnInfo.setNullable("YES".equals(colRet.getString("IS_NULLABLE")));
                columnInfo.setPrimaryKey(primaryKeyMap.containsKey(columnInfo.getColumnName()));
                tableInfo.addColumnInfo(columnInfo.getColumnName(), columnInfo);
            }
            JdbcUtils.close(colRet);
            JdbcUtils.close(pkRet);
            logger.debug("***************************************************");
            tableInfoList.add(tableInfo);
        }
    }

    private TableCommentGetter getTableCommentGetter(Connection connection) throws SQLException {
        String dbName = connection.getMetaData().getDatabaseProductName();
        if (dbName != null) {
            if (dbName.toUpperCase().indexOf("ORACLE") != -1) {
                return new OracleTableCommentGetter(connection);
            }
            if (dbName.toUpperCase().indexOf("MYSQL") != -1) {
                return new MySQLTableCommentGetter(connection);
            }
        }
        return new DefaultTableCommentGetter();
    }

}
