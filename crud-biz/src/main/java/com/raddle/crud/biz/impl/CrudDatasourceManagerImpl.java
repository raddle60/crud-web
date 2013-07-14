package com.raddle.crud.biz.impl;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Service;

import com.raddle.crud.biz.CrudDatasourceManager;
import com.raddle.crud.dao.CrudDatasourceDao;
import com.raddle.crud.model.toolgen.CrudDatasource;

/**
 * 类CrudDatasourceManagerImpl.java的实现描述：获得数据源
 * @author raddle60 2013-3-7 下午11:19:55
 */
@Service("crudDatasourceManager")
public class CrudDatasourceManagerImpl implements CrudDatasourceManager {

    private static ThreadLocal<Map<Long, DataSource>> datasourceThreadCache = new ThreadLocal<Map<Long, DataSource>>();

    @Autowired
    private CrudDatasourceDao crudDatasourceDao;

    @Override
    public DataSource getDatasource(Long id) {
        if (datasourceThreadCache.get() == null) {
            datasourceThreadCache.set(new HashMap<Long, DataSource>());
        }
        if (datasourceThreadCache.get().containsKey(id)) {
            return datasourceThreadCache.get().get(id);
        }
        CrudDatasource crudDatasource = crudDatasourceDao.selectByPrimaryKey(id);
        DriverManagerDataSource ds = new DriverManagerDataSource();
        ds.setDriverClassName(crudDatasource.getDirverClassName());
        ds.setUrl(crudDatasource.getUrl());
        ds.setUsername(crudDatasource.getUsername());
        ds.setPassword(crudDatasource.getPassword());
        datasourceThreadCache.get().put(id, ds);
        return ds;
    }

    public static void clearThreadCache() {
        datasourceThreadCache.remove();
    }
}
