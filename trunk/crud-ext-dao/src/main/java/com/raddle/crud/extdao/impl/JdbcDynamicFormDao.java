package com.raddle.crud.extdao.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ConnectionCallback;
import org.springframework.jdbc.core.JdbcTemplate;

import com.raddle.crud.extdao.DynamicFormDao;
import com.raddle.crud.extdao.dbinfo.TableMetaHelper;
import com.raddle.crud.extdao.dbinfo.model.TableInfo;

/**
 * 类JdbcDynamicFormDao.java的实现描述：动态表单的dao
 * @author raddle60 2013-3-7 下午10:02:24
 */
public class JdbcDynamicFormDao implements DynamicFormDao {

    private DataSource dataSource;

    public JdbcDynamicFormDao(){
    }

    public JdbcDynamicFormDao(DataSource dataSource){
        this.dataSource = dataSource;
    }

    @Override
    public TableInfo getTableInfo(final String tableName) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        return jdbcTemplate.execute(new ConnectionCallback<TableInfo>() {

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
    }

    public DataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

}
