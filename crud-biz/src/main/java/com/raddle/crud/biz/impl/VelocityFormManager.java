package com.raddle.crud.biz.impl;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.apache.commons.lang.StringUtils;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import com.raddle.crud.biz.CrudDatasourceManager;
import com.raddle.crud.biz.DynamicFormManager;
import com.raddle.crud.enums.OptionType;
import com.raddle.crud.extdao.DynamicFormDao;
import com.raddle.crud.extdao.dbinfo.model.ColumnInfo;
import com.raddle.crud.extdao.dbinfo.model.TableInfo;
import com.raddle.crud.extdao.impl.JdbcDynamicFormDao;
import com.raddle.crud.model.toolgen.CrudItem;

/**
 * 类DynamicFormManagerImpl.java的实现描述：动态表单
 * @author raddle60 2013-3-9 下午5:21:19
 */
public class VelocityFormManager implements DynamicFormManager, InitializingBean {

    private static ThreadLocal<Map<String, List<Map<String, Object>>>> optionThreadCache = new ThreadLocal<Map<String, List<Map<String, Object>>>>();

    private Properties velocityProps;

    private VelocityEngine velocityEngine;

    @Autowired
    private CrudDatasourceManager datasourceManager;

    @Resource(name = "crudDataSource")
    private DataSource crudDataSource;

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
        Assert.hasLength(dynamicUpdateSql, "sql不能为空");
        StringWriter parsedSql = new StringWriter();
        VelocityContext context = new VelocityContext(params);
        velocityEngine.evaluate(context, parsedSql, "dynamicUpdateSql", dynamicUpdateSql);
        DynamicFormDao dynamicFormDao = new JdbcDynamicFormDao(dataSource);
        return dynamicFormDao.update(parsedSql.toString(), contextToMap(context));
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
    
    private String getTableSchemaPrefix(String tableSchema) {
        if (StringUtils.isNotEmpty(tableSchema)) {
            return tableSchema + ".";
        } else {
            return "";
        }
    }

    @Override
    public String generateDynamicSelectSql(String tableSchema, String tableName, DataSource dataSource) {
        DynamicFormDao dynamicFormDao = new JdbcDynamicFormDao(dataSource);
        TableInfo tableInfo = dynamicFormDao.getTableInfo(tableSchema, tableName);
        StringBuilder sb = new StringBuilder();
        sb.append("select ");
        int i = 0;
        for (ColumnInfo columnInfo : tableInfo.getColumnInfos()) {
            if (i > 0) {
                sb.append(",");
            }
            sb.append(columnInfo.getColumnName());
            i++;
        }
        sb.append(" from " + getTableSchemaPrefix(tableSchema) + tableName + " where 1=1 \n");
        for (ColumnInfo columnInfo : tableInfo.getColumnInfos()) {
            sb.append(" #if($" + columnInfo.getColumnName().toLowerCase() + ") \n");
            sb.append(" and " + columnInfo.getColumnName() + "= :" + columnInfo.getColumnName().toLowerCase() + " \n");
            sb.append(" #end \n");
        }
        for (ColumnInfo columnInfo : tableInfo.getColumnInfos()) {
            if (columnInfo.isPrimaryKey()) {
                sb.append(" order by " + columnInfo.getColumnName() + " desc");
                break;
            }
        }
        return sb.toString();
    }

    @Override
    public String generateDynamicInsertSql(String tableSchema, String tableName, DataSource dataSource) {
        DynamicFormDao dynamicFormDao = new JdbcDynamicFormDao(dataSource);
        TableInfo tableInfo = dynamicFormDao.getTableInfo(tableSchema, tableName);
        StringBuilder sb = new StringBuilder();
        sb.append("insert into " + getTableSchemaPrefix(tableSchema) + tableName + " ( ");
        sb.append(" #set($hasInsert = false) \n");
        for (ColumnInfo columnInfo : tableInfo.getColumnInfos()) {
            sb.append(" #if($" + columnInfo.getColumnName().toLowerCase() + ") \n");
            sb.append("   #if($hasInsert) , #end \n");
            sb.append("   " + columnInfo.getColumnName() + " \n");
            sb.append("   #set($hasInsert = true) \n");
            sb.append(" #end \n");
        }
        sb.append(" ) values ( \n");
        sb.append(" #set($hasInsert = false) \n");
        for (ColumnInfo columnInfo : tableInfo.getColumnInfos()) {
            sb.append(" #if($" + columnInfo.getColumnName().toLowerCase() + ") \n");
            sb.append("   #if($hasInsert) , #end \n");
            sb.append("   :" + columnInfo.getColumnName().toLowerCase() + " \n");
            sb.append("   #set($hasInsert = true) \n");
            sb.append(" #end \n");
        }
        sb.append(" ) ");
        return sb.toString();
    }

    @Override
    public String generateDynamicUpdateSql(String tableSchema, String tableName, DataSource dataSource) {
        DynamicFormDao dynamicFormDao = new JdbcDynamicFormDao(dataSource);
        TableInfo tableInfo = dynamicFormDao.getTableInfo(tableSchema, tableName);
        StringBuilder sb = new StringBuilder();
        sb.append("update " + getTableSchemaPrefix(tableSchema) + tableName + " set ");
        sb.append(" #set($hasUpdate = false) \n");
        for (ColumnInfo columnInfo : tableInfo.getColumnInfos()) {
            sb.append(" #if($" + columnInfo.getColumnName().toLowerCase() + ") \n");
            sb.append("   #if($hasUpdate) , #end \n");
            sb.append("   " + columnInfo.getColumnName() + "= :" + columnInfo.getColumnName().toLowerCase() + " \n");
            sb.append("   #set($hasUpdate = true) \n");
            sb.append(" #end \n");
        }
        sb.append(" where 1=1 \n");
        boolean hasPk = false;
        for (ColumnInfo columnInfo : tableInfo.getColumnInfos()) {
            if (columnInfo.isPrimaryKey()) {
                sb.append(" and " + columnInfo.getColumnName() + "= :" + columnInfo.getColumnName().toLowerCase() + " \n");
                hasPk = true;
            }
        }
        if (!hasPk) {
            throw new IllegalStateException("表" + tableName + "没有主键");
        }
        return sb.toString();
    }

    @Override
    public String generateDynamicDeleteSql(String tableSchema, String tableName, DataSource dataSource) {
        DynamicFormDao dynamicFormDao = new JdbcDynamicFormDao(dataSource);
        TableInfo tableInfo = dynamicFormDao.getTableInfo(tableSchema, tableName);
        StringBuilder sb = new StringBuilder();
        sb.append("delete from " + getTableSchemaPrefix(tableSchema) + tableName + " ");
        sb.append(" where 1=1 \n");
        boolean hasPk = false;
        for (ColumnInfo columnInfo : tableInfo.getColumnInfos()) {
            if (columnInfo.isPrimaryKey()) {
                sb.append(" and " + columnInfo.getColumnName() + "= :" + columnInfo.getColumnName().toLowerCase() + " \n");
                hasPk = true;
            }
        }
        if (!hasPk) {
            throw new IllegalStateException("表" + tableName + "没有主键");
        }
        return sb.toString();
    }

    @Override
    public List<Map<String, Object>> getItemOptions(CrudItem crudItem) {
        Assert.hasText(crudItem.getOptionType(), "选项类型为空");
        if (optionThreadCache.get() == null) {
            optionThreadCache.set(new HashMap<String, List<Map<String, Object>>>());
        }
        String cacheKey = crudItem.getId().toString();
        if (OptionType.STATIC.name().equals(crudItem.getOptionType()) || OptionType.ENUM_TABLE.name().equals(crudItem.getOptionType())) {
            cacheKey = crudItem.getOptionValue();
        }
        if (OptionType.SQL.name().equals(crudItem.getOptionType())) {
            cacheKey = crudItem.getCrudDsId() + "_" + crudItem.getOptionValue();
        }
        if (optionThreadCache.get().containsKey(cacheKey)) {
            return optionThreadCache.get().get(cacheKey);
        }
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        if (OptionType.STATIC.name().equals(crudItem.getOptionType())) {
            if (StringUtils.isNotBlank(crudItem.getOptionValue())) {
                // 逗号分割
                String[] split = StringUtils.split(crudItem.getOptionValue(), ",");
                for (String option : split) {
                    option = StringUtils.trim(option);
                    if (StringUtils.isNotEmpty(option)) {
                        if (option.indexOf(":") != -1) {
                            // 冒号分隔的keyvalue
                            String[] options = StringUtils.split(option, ":");
                            if (options.length > 1) {
                                Map<String, Object> map = new HashMap<String, Object>();
                                map.put("key", options[0]);
                                map.put("value", options[1]);
                                list.add(map);
                            }
                        } else {
                            Map<String, Object> map = new HashMap<String, Object>();
                            map.put("key", option);
                            map.put("value", option);
                            list.add(map);
                        }
                    }
                }
            }
        }
        if (OptionType.ENUM_TABLE.name().equals(crudItem.getOptionType())) {
            HashMap<String, Object> hashMap = new HashMap<String, Object>();
            hashMap.put("enumType", crudItem.getOptionValue());
            return queryForList("select key,value from crud_enums where enum_type = :enumType", hashMap, crudDataSource);
        }
        if (OptionType.SQL.name().equals(crudItem.getOptionType())) {
            Assert.hasText(crudItem.getOptionValue(), "选项sql为空");
            Assert.notNull(crudItem.getCrudDsId(), "选项数据源为空");
            return queryForList(crudItem.getOptionValue(), new HashMap<String, Object>(), datasourceManager.getDatasource(crudItem.getCrudDsId()));
        }
        optionThreadCache.get().put(cacheKey, list);
        return list;
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

    public static void clearThreadCache() {
        optionThreadCache.remove();
    }
}
