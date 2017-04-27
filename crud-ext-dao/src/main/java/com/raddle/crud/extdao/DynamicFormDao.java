package com.raddle.crud.extdao;

import java.util.List;
import java.util.Map;

import com.raddle.crud.extdao.dbinfo.model.TableInfo;

/**
 * 类DynamicFormDao.java的实现描述：动态form的dao
 * @author raddle60 2013-3-7 下午10:01:01
 */
public interface DynamicFormDao {

    /**
     * 根据表名获得列信息
     * @param tableSchema
     * @param tableName
     * @return
     */
    public TableInfo getTableInfo(String tableSchema ,String tableName);

    /**
     * 执行select语句
     * @param selectSql
     * @param params
     * @return 列表
     */
    public List<Map<String, Object>> queryForList(String selectSql, Map<String, Object> params);

    /**
     * 执行select语句
     * @param selectSql
     * @param params
     * @return 单条记录
     */
    public Map<String, Object> queryForObject(String selectSql, Map<String, Object> params);

    /**
     * 执行update语句
     * @param updateSql
     * @param params
     * @return 更新条数
     */
    public int update(String updateSql, Map<String, Object> params);
}
