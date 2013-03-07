package com.raddle.crud.extdao.impl;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import com.raddle.crud.extdao.dbinfo.model.TableInfo;

public class JdbcDynamicFormDaoTest {

    private static JdbcDynamicFormDao jdbcDynamicFormDao = new JdbcDynamicFormDao();

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        DriverManagerDataSource ds = new DriverManagerDataSource();
        ds.setDriverClassName("oracle.jdbc.driver.OracleDriver");
        ds.setUrl("jdbc:oracle:thin:@localhost:1521:xe");
        ds.setUsername("crudweb");
        ds.setPassword("111111");
        jdbcDynamicFormDao.setDataSource(ds);
    }

    @Test
    public void testGetTableInfo() {
        TableInfo tableInfo = jdbcDynamicFormDao.getTableInfo("TEST_CRUD");
        System.out.println(tableInfo.getColumnInfos().size());
    }

}
