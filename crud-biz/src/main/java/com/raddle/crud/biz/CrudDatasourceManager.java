package com.raddle.crud.biz;

import javax.sql.DataSource;

import com.raddle.crud.model.toolgen.CrudDatasource;

/**
 * 类CrudDefinitionManager.java的实现描述：
 * @author raddle60 2013-3-5 下午10:04:28
 */
public interface CrudDatasourceManager {

    /**
     * 获得数据源
     * @param id
     * @return
     */
    public DataSource getDatasource(Long id);
    
    /**
     * 获得数据源
     * @param code
     * @param envCode
     * @return
     */
    public CrudDatasource getDatasource(String code, String envCode);
}
