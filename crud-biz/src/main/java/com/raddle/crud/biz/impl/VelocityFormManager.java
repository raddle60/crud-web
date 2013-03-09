package com.raddle.crud.biz.impl;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

import com.raddle.crud.biz.DynamicFormManager;
import com.raddle.crud.extdao.DynamicFormDao;
import com.raddle.crud.extdao.impl.JdbcDynamicFormDao;

/**
 * 类DynamicFormManagerImpl.java的实现描述：动态表单
 * @author raddle60 2013-3-9 下午5:21:19
 */
public class VelocityFormManager implements DynamicFormManager, InitializingBean {

    private Properties velocityProps;

    private VelocityEngine velocityEngine;

    @Override
    public List<Map<String, Object>> queryForList(String dynamicSelectSql, Map<String, Object> params, DataSource dataSource) {
        Assert.hasLength(dynamicSelectSql, "sql不能为空");
        StringWriter parsedSql = new StringWriter();
        VelocityContext context = new VelocityContext(params);
        velocityEngine.evaluate(context, parsedSql, "dynamicSelectSql", dynamicSelectSql);
        DynamicFormDao dynamicFormDao = new JdbcDynamicFormDao(dataSource);
        return dynamicFormDao.queryForList(parsedSql.toString(), contextToMap(context));
    }

    @Override
    public Map<String, Object> queryForObject(String dynamicSelectSql, Map<String, Object> params, DataSource dataSource) {
        Assert.hasLength(dynamicSelectSql, "sql不能为空");
        StringWriter parsedSql = new StringWriter();
        VelocityContext context = new VelocityContext(params);
        velocityEngine.evaluate(context, parsedSql, "dynamicSelectSql", dynamicSelectSql);
        DynamicFormDao dynamicFormDao = new JdbcDynamicFormDao(dataSource);
        return dynamicFormDao.queryForObject(parsedSql.toString(), contextToMap(context));
    }

    @Override
    public int update(String dynamicUpdateSql, Map<String, Object> params, DataSource dataSource) {
        // TODO Auto-generated method stub
        return 0;
    }

    /**
     * vm中可能会设置新的变量，要放会map中
     * @param context
     * @return
     */
    private Map<String, Object> contextToMap(VelocityContext context) {
        Map<String, Object> map = new HashMap<String, Object>();
        for (Object key : context.getKeys()) {
            map.put(key + "", context.get(key + ""));
        }
        return map;
    }

    @Override
    public String generateDynamicSelectSql(String tableName, DataSource dataSource) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String generateDynamicUpdateSql(String tableName, DataSource dataSource) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String generateDynamicDeleteSql(String tableName, DataSource dataSource) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        velocityEngine = new VelocityEngine();
        if (velocityProps != null) {
            velocityEngine.init(velocityProps);
        }
    }

    public Properties getVelocityProps() {
        return velocityProps;
    }

    public void setVelocityProps(Properties velocityProps) {
        this.velocityProps = velocityProps;
    }

}
