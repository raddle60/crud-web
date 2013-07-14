package com.raddle.crud.extdao.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.lang.StringUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.ColumnMapRowMapper;
import org.springframework.jdbc.core.ConnectionCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.raddle.crud.extdao.DynamicFormDao;
import com.raddle.crud.extdao.dbinfo.TableMetaHelper;
import com.raddle.crud.extdao.dbinfo.model.TableInfo;

/**
 * 类JdbcDynamicFormDao.java的实现描述：动态表单的dao
 * @author raddle60 2013-3-7 下午10:02:24
 */
public class JdbcDynamicFormDao implements DynamicFormDao {

    private static ThreadLocal<Map<String, TableInfo>> tableInfoThreadCache = new ThreadLocal<Map<String, TableInfo>>();

    private DataSource dataSource;

    public JdbcDynamicFormDao(){
    }

    public JdbcDynamicFormDao(DataSource dataSource){
        this.dataSource = dataSource;
    }

    @Override
    public TableInfo getTableInfo(final String tableName) {
        if (tableInfoThreadCache.get() == null) {
            tableInfoThreadCache.set(new HashMap<String, TableInfo>());
        }
        if (tableInfoThreadCache.get().containsKey(tableName)) {
            return tableInfoThreadCache.get().get(tableName);
        }
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        TableInfo tableInfo = jdbcTemplate.execute(new ConnectionCallback<TableInfo>() {

            @Override
            public TableInfo doInConnection(Connection con) throws SQLException, DataAccessException {
                TableMetaHelper metaHelper = new TableMetaHelper(con);
                List<TableInfo> tableInfo = metaHelper.getTableInfo(new String[] { tableName }, new String[] { "TABLE", "VIEW", "SYNONYM" });
                if (tableInfo.size() > 0) {
                    return tableInfo.get(0);
                }
                return null;
            }
        });
        tableInfoThreadCache.get().put(tableName, tableInfo);
        return tableInfo;
    }

    @Override
    public List<Map<String, Object>> queryForList(String selectSql, Map<String, Object> params) {
        if (!selectSql.trim().toLowerCase().startsWith("select")) {
            throw new IllegalArgumentException("不是select语句");
        }
        NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        return jdbcTemplate.query(selectSql, params, new ColumnMapRowMapper() {

            @Override
            protected String getColumnKey(String columnName) {
                return StringUtils.lowerCase(columnName);
            }

        });
    }

    @Override
    public Map<String, Object> queryForObject(String selectSql, Map<String, Object> params) {
        List<Map<String, Object>> queryForList = queryForList(selectSql, params);
        if (queryForList.size() > 1) {
            throw new IncorrectResultSizeDataAccessException(1, queryForList.size());
        }
        if (queryForList.size() > 0) {
            return queryForList.get(0);
        } else {
            return null;
        }
    }

    @Override
    public int update(String updateSql, Map<String, Object> params) {
        if (updateSql.trim().toLowerCase().startsWith("select")) {
            throw new IllegalArgumentException("不是update语句");
        }
        NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        return jdbcTemplate.update(updateSql, params);
    }

    public DataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public static void clearThreadCache() {
        tableInfoThreadCache.remove();
    }
}
