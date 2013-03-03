package com.raddle.crud.dao;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.raddle.crud.model.toolgen.CrudDatasource;
import com.raddle.utils.TestModelDataInitUtils;

public class CrudDatasourceDaoTest extends BaseDaoTest {

    @Autowired
    private CrudDatasourceDao crudDatasourceDao;

    @Test
    public void testInsert() {
        CrudDatasource record = new CrudDatasource();
        TestModelDataInitUtils.initData(record);
        int insert = crudDatasourceDao.insert(record);
        System.out.println(insert);
        System.out.println(record.getId());
        CrudDatasource crudDatasource = crudDatasourceDao.selectByPrimaryKey(record.getId());
        System.out.println(crudDatasource.getCreatedAt());
    }

}
