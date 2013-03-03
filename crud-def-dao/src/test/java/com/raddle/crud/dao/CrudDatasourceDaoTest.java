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
        crudDatasourceDao.insert(record);
    }

}
