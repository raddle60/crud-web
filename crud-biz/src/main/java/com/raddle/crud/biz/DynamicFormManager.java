package com.raddle.crud.biz;

import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

public interface DynamicFormManager {

    /**
     * 执行动态select语句
     * @param dynamicSelectSql
     * @param params
     * @param dataSource
     * @return 列表
     */
    public List<Map<String, Object>> queryForList(String dynamicSelectSql, Map<String, Object> params, DataSource dataSource);

    /**
     * 执行动态select语句
     * @param dynamicSelectSql
     * @param params
     * @param dataSource
     * @return 单条记录
     */
    public Map<String, Object> queryForObject(String dynamicSelectSql, Map<String, Object> params, DataSource dataSource);

    /**
     * 执行动态insert，update，delete语句
     * @param dynamicUpdateSql
     * @param params
     * @param dataSource
     * @return 单条记录
     */
    public int update(String dynamicUpdateSql, Map<String, Object> params, DataSource dataSource);

    /**
     * 根据表自动生成查询sql
     * @param tableName
     * @param dataSource
     * @return
     */
    public String generateDynamicSelectSql(String tableName, DataSource dataSource);

    /**
     * 根据表自动生成插入sql
     * @param tableName
     * @param dataSource
     * @return
     */
    public String generateDynamicInsertSql(String tableName, DataSource dataSource);

    /**
     * 根据表自动生成更新sql
     * @param tableName
     * @param dataSource
     * @return
     */
    public String generateDynamicUpdateSql(String tableName, DataSource dataSource);

    /**
     * 根据表自动生成删除sql
     * @param tableName
     * @param dataSource
     * @return
     */
    public String generateDynamicDeleteSql(String tableName, DataSource dataSource);

}
