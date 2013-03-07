package com.raddle.crud.extdao;

import com.raddle.crud.extdao.dbinfo.model.TableInfo;

/**
 * 类DynamicFormDao.java的实现描述：动态form的dao
 * @author raddle60 2013-3-7 下午10:01:01
 */
public interface DynamicFormDao {

    public TableInfo getTableInfo(String tableName);
}
