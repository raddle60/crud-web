package com.raddle.crud.biz.impl;

import java.beans.PropertyVetoException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.mchange.v2.c3p0.PooledDataSource;
import com.raddle.crud.biz.CrudDatasourceManager;
import com.raddle.crud.dao.CrudDatasourceDao;
import com.raddle.crud.model.toolgen.CrudDatasource;
import com.raddle.crud.model.toolgen.CrudDatasourceExample;

/**
 * 类CrudDatasourceManagerImpl.java的实现描述：获得数据源
 * @author raddle60 2013-3-7 下午11:19:55
 */
@Service("crudDatasourceManager")
public class CrudDatasourceManagerImpl implements CrudDatasourceManager {

    private static ThreadLocal<Map<Long, CachedDataSource>> datasourceThreadCache = new ThreadLocal<Map<Long, CachedDataSource>>();

    @Autowired
    private CrudDatasourceDao crudDatasourceDao;

    @Override
    public DataSource getDatasource(Long id) {
        if (datasourceThreadCache.get() == null) {
            datasourceThreadCache.set(new HashMap<Long, CachedDataSource>());
        }
        CrudDatasource crudDatasource = crudDatasourceDao.selectByPrimaryKey(id);
        if (datasourceThreadCache.get().containsKey(id)) {
            if (datasourceThreadCache.get().get(id).isSameDs(crudDatasource)) {
                return datasourceThreadCache.get().get(id).dataSource;
            } else {
                // 关闭数据源
                PooledDataSource dataSource = datasourceThreadCache.get().get(id).dataSource;
                try {
                    dataSource.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e.getMessage(), e);
                }
                datasourceThreadCache.get().remove(id);
            }
        }
        ComboPooledDataSource cpds = new ComboPooledDataSource();
        try {
            cpds.setDriverClass(crudDatasource.getDirverClassName()); //loads the jdbc driver 
        } catch (PropertyVetoException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
        cpds.setJdbcUrl(crudDatasource.getUrl());
        cpds.setUser(crudDatasource.getUsername());
        cpds.setPassword(crudDatasource.getPassword()); // the settings below are optional -- c3p0 can work with defaults 
        cpds.setMinPoolSize(1);
        cpds.setAcquireIncrement(2);
        cpds.setMaxPoolSize(10);
        cpds.setMaxIdleTime(1800);
        cpds.setIdleConnectionTestPeriod(300);
        datasourceThreadCache.get().put(id, new CachedDataSource(crudDatasource, cpds));
        return cpds;
    }

    @Override
    public CrudDatasource getDatasource(String code, String envCode) {
        CrudDatasourceExample example = new CrudDatasourceExample();
        example.createCriteria().andCodeEqualTo(code).andEnvCodeEqualTo(envCode);

        List<CrudDatasource> list = crudDatasourceDao.selectByExample(example);
        if (list.size() == 1) {
            return list.get(0);
        }
        return null;
    }

    public static void clearThreadCache() {
        datasourceThreadCache.remove();
    }

    private class CachedDataSource {
        private CrudDatasource crudDatasource;
        private PooledDataSource dataSource;

        public CachedDataSource(CrudDatasource crudDatasource, PooledDataSource dataSource) {
            this.crudDatasource = crudDatasource;
            this.dataSource = dataSource;
        }

        public boolean isSameDs(CrudDatasource other) {
            if (other == null) {
                return false;
            }
            return crudDatasource.getDirverClassName().equals(other.getDirverClassName()) && crudDatasource.getUrl().equals(other.getUrl()) && crudDatasource.getUsername().equals(other.getUsername())
                    && crudDatasource.getPassword().equals(other.getPassword());
        }
    }

}
