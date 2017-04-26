package com.raddle.crud.web.controller;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.velocity.VelocityContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.support.RequestContext;
import org.springframework.web.servlet.view.velocity.VelocityConfig;

import com.raddle.crud.biz.CrudDatasourceManager;
import com.raddle.crud.biz.DynamicFormManager;
import com.raddle.crud.dao.CrudDatasourceDao;
import com.raddle.crud.dao.CrudDefinitionDao;
import com.raddle.crud.dao.CrudItemDao;
import com.raddle.crud.enums.ActionType;
import com.raddle.crud.enums.DefType;
import com.raddle.crud.enums.ItemFkType;
import com.raddle.crud.enums.ItemType;
import com.raddle.crud.extdao.DynamicFormDao;
import com.raddle.crud.extdao.dbinfo.model.ColumnInfo;
import com.raddle.crud.extdao.dbinfo.model.TableInfo;
import com.raddle.crud.extdao.impl.JdbcDynamicFormDao;
import com.raddle.crud.model.toolgen.CrudDatasource;
import com.raddle.crud.model.toolgen.CrudDefinition;
import com.raddle.crud.model.toolgen.CrudDefinitionExample;
import com.raddle.crud.model.toolgen.CrudItem;
import com.raddle.crud.model.toolgen.CrudItemExample;
import com.raddle.crud.model.toolgen.CrudItemExample.Criteria;
import com.raddle.crud.vo.CommonResult;
import com.raddle.crud.web.toolbox.DynamicFormTool;
import com.raddle.crud.web.toolbox.SqlUtils;

/**
 * 类FormController.java的实现描述：表单页面
 * @author raddle60 2013-3-9 下午3:41:30
 */
@Controller
public class FormController extends BaseController {

    @Autowired
    private CrudDefinitionDao crudDefinitionDao;

    @Autowired
    private CrudDatasourceDao crudDatasourceDao;
    
    @Autowired
    private CrudItemDao crudItemDao;

    @Autowired
    private CrudDatasourceManager datasourceManager;

    @Autowired
    private DynamicFormManager dynamicFormManager;

    @Resource(name = "dynamicFormTool")
    private DynamicFormTool dynamicFormTool;

    @Autowired
    private VelocityConfig velocityConfig;

    @RequestMapping(value = "form/show")
    public String showForm(Long defId, String envCode, ModelMap model, HttpServletResponse response, HttpServletRequest request) {
        if (defId == null) {
            throw new RuntimeException("表单id不能为空");
        }
        model.put("formTool", dynamicFormTool);
        if (StringUtils.isNotEmpty(envCode)) {
            request.getSession().setAttribute("curEnvCode", envCode);
            model.put("envCode", envCode);
        } else {
            model.put("envCode", request.getSession().getAttribute("curEnvCode"));
        }
        CrudDefinition crudDefinition = crudDefinitionDao.selectByPrimaryKey(defId);
        if (crudDefinition.getDefType().equals(DefType.LIST.name())) {
            return toListResult(crudDefinition, model, null, request);
        } else if (crudDefinition.getDefType().equals(DefType.MULTI_LIST.name())) {
            return toMultiListResult(crudDefinition, model, null, request);
        } else {
            return toSingleResult(crudDefinition, model, null, request);
        }
    }
    
    private CrudDatasource getDatasourceByEnvCode(CrudDefinition crudDefinition, HttpServletRequest request) {
        String curEnvCode = (String) request.getSession().getAttribute("curEnvCode");
        CrudDatasource crudDatasource = crudDatasourceDao.selectByPrimaryKey(crudDefinition.getCrudDsId());
        CrudDatasource retCrudDatasource = null;
        if (StringUtils.isNotEmpty(curEnvCode)) {
            retCrudDatasource = datasourceManager.getDatasource(crudDatasource.getCode(), curEnvCode);
        }
        if (retCrudDatasource == null) {
            retCrudDatasource = crudDatasource;
        }
        return retCrudDatasource;
    }

    private String toSingleResult(CrudDefinition crudDefinition, ModelMap model, Map<String, Object> extraParams, HttpServletRequest request) {
        long start = System.currentTimeMillis();
        String readSql = getReadSql(crudDefinition);
        if (StringUtils.isNotBlank(readSql)) {
            CrudDatasource crudDatasource = getDatasourceByEnvCode(crudDefinition, request);
            model.put("dsConfig", crudDatasource);
            Object result = dynamicFormManager.queryForObject(readSql, createParams(request, extraParams), datasourceManager.getDatasource(crudDatasource.getId()));
            model.put("result", result);
        } else if (DefType.ADD.name().equals(crudDefinition.getDefType())) {
            // 如果是新增而且不从数据库取值,用请求里的
            model.put("result", createParams(request, extraParams));
        }
        List<CrudItem> defItems = queryDefItems(crudDefinition);
        model.put("defItems", defItems);
        model.put("def", crudDefinition);
        model.put("duration", (System.currentTimeMillis() - start) + "ms");
        return "form/show";
    }

    private List<CrudItem> queryDefItems(CrudDefinition crudDefinition) {
        CrudItemExample where = new CrudItemExample();
        Criteria criteria = where.createCriteria();
        criteria.andDeletedEqualTo((short) 0);
        criteria.andCrudDefIdEqualTo(crudDefinition.getId());
        criteria.andFkTypeEqualTo(ItemFkType.DEF.name());
        where.setOrderByClause("item_order");
        List<CrudItem> defItems = crudItemDao.selectByExample(where);
        return defItems;
    }

    private String toListResult(CrudDefinition crudDefinition, ModelMap model, Map<String, Object> extraParams, HttpServletRequest request) {
        long start = System.currentTimeMillis();
        String readSql = getReadSql(crudDefinition);
        CrudDatasource crudDatasource = getDatasourceByEnvCode(crudDefinition, request);
        model.put("dsConfig", crudDatasource);
        Object result = dynamicFormManager.queryForList(readSql, createParams(request, extraParams), datasourceManager.getDatasource(crudDatasource.getId()));
        model.put("result", result);
        List<CrudItem> whereItems = queryDefItems(crudDefinition);
        model.put("defWheres", whereItems);
        List<CrudItem> defListItems = queryDefListItems(crudDefinition);
        if (new Short((short) 1).equals(crudDefinition.getAutoMatchBtn()) && StringUtils.isNotBlank(crudDefinition.getTableName())) {
            putAddDef(crudDefinition, model);
            putUpdateDef(crudDefinition, defListItems, request);
        }
        model.put("defCols", defListItems);
        model.put("def", crudDefinition);
        model.put("params", createParams(request, extraParams));
        model.put("duration", (System.currentTimeMillis() - start) + "ms");
        return "form/list";
    }

    @SuppressWarnings("unchecked")
    private String toMultiListResult(CrudDefinition crudDefinition, ModelMap model, Map<String, Object> extraParams, HttpServletRequest request) {
        long start = System.currentTimeMillis();
        List<CrudItem> whereItems = queryDefItems(crudDefinition);
        model.put("defWheres", whereItems);
        model.put("def", crudDefinition);
        model.put("params", createParams(request, extraParams));
        Map<String, Object> subResults = new HashMap<String, Object>();
        Matcher matcher = DefinitionController.COMP_DEF_ID_PATTERN.matcher(crudDefinition.getCompositeTemplate());
        Set<Long> generated = new HashSet<Long>();
        while (matcher.find()) {
            Long defId = Long.parseLong(matcher.group(1));
            if (generated.contains(defId)) {
                continue;
            }
            CrudDefinition subDef = crudDefinitionDao.selectByPrimaryKey(defId);
            if (subDef != null && (subDef.getDefType().equals(DefType.LIST.name()) || subDef.getDefType().equals(DefType.VIEW.name()))) {
                ModelMap subModel = new ModelMap();
                subModel.put("formTool", dynamicFormTool);
                subModel.put("request", request);
                subModel.put("isInMultiList", "true");
                subModel.put("springMacroRequestContext", new RequestContext(request));
                subModel.put("stringUtils", new StringUtils());
                subModel.put("stringEscapeUtils", new StringEscapeUtils());
                subModel.put("dateUtils", new DateUtils());
                subModel.put("dateFormatUtils", new DateFormatUtils());
                if (subDef.getDefType().equals(DefType.LIST.name())) {
                    try {
                        String templateName = toListResult(subDef, subModel, subResults, request) + ".vm";
                        List<Object> result = (List<Object>) subModel.get("result");
                        if (result.size() > 0) {
                            subResults.put("result_" + defId, result);
                            subResults.put("result_" + defId + "_first", result.get(0));
                            StringWriter sw = new StringWriter();
                            velocityConfig.getVelocityEngine().mergeTemplate(templateName, "utf-8", new VelocityContext(subModel), sw);
                            model.put("def_" + defId, sw.toString());
                        }
                    } catch (Exception e) {
                        logger.error(e.getMessage(), e);
                        model.put("def_" + defId, "def_" + defId + "异常:" + e.getMessage());
                    }
                } else if (subDef.getDefType().equals(DefType.VIEW.name())) {
                    try {
                        String templateName = toSingleResult(subDef, subModel, subResults, request) + ".vm";
                        Object result = subModel.get("result");
                        if (result != null) {
                            subResults.put("result_" + defId, result);
                            StringWriter sw = new StringWriter();
                            velocityConfig.getVelocityEngine().mergeTemplate(templateName, "utf-8", new VelocityContext(subModel), sw);
                            model.put("def_" + defId, sw.toString());
                        }
                    } catch (Exception e) {
                        logger.error(e.getMessage(), e);
                        model.put("def_" + defId, "def_" + defId + "异常:" + e.getMessage());
                    }
                }
            }
            generated.add(defId);
        }
        StringWriter sw = new StringWriter();
        try {
            velocityConfig.getVelocityEngine().evaluate(new VelocityContext(model), sw, "compositeTempl", crudDefinition.getCompositeTemplate());
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            sw.write(e.getMessage());
        }
        model.put("noescape_composite_content", sw.toString());
        model.put("duration", (System.currentTimeMillis() - start) + "ms");
        return "form/multi_list";
    }

    private void putUpdateDef(CrudDefinition crudDefinition, List<CrudItem> defListItems, HttpServletRequest request) {
        CrudDefinitionExample updateWhere = new CrudDefinitionExample();
        com.raddle.crud.model.toolgen.CrudDefinitionExample.Criteria criteria = updateWhere.createCriteria();
        criteria.andDeletedEqualTo((short) 0);
        criteria.andTableNameEqualTo(crudDefinition.getTableName());
        criteria.andDefTypeIn(Arrays.asList(new String[] { DefType.EDIT.name(), DefType.VIEW.name(), DefType.DELETE.name() }));
        List<CrudDefinition> updaeList = crudDefinitionDao.selectByExample(updateWhere);
        if (updaeList.size() > 0) {
            for (CrudDefinition updateDef : updaeList) {
                // 创建按钮列
                CrudItem actionItem = new CrudItem();
                actionItem.setCrudDefId(crudDefinition.getId());
                actionItem.setTitle(updateDef.getDefType());
                actionItem.setItemType(ItemType.ACTION.name());
                actionItem.setActionType(ActionType.HREF.name());
                StringBuilder href = new StringBuilder();
                href.append(request.getContextPath() + "/form/show.htm?defId=" + updateDef.getId());
                DynamicFormDao dynamicFormDao = new JdbcDynamicFormDao(datasourceManager.getDatasource(crudDefinition.getCrudDsId()));
                TableInfo tableInfo = dynamicFormDao.getTableInfo(crudDefinition.getTableName());
                boolean hasPk = false;
                for (ColumnInfo columnInfo : tableInfo.getColumnInfos()) {
                    if (columnInfo.isPrimaryKey()) {
                        hasPk = true;
                        href.append("&" + columnInfo.getColumnName().toLowerCase() + "=${" + columnInfo.getColumnName().toLowerCase() + "}");
                    }
                }
                if (!hasPk) {
                    throw new IllegalStateException("表" + crudDefinition.getTableName() + "没有主键");
                }
                actionItem.setHref(href.toString());
                defListItems.add(actionItem);
            }
        }
    }

    private void putAddDef(CrudDefinition crudDefinition, ModelMap model) {
        CrudDefinitionExample addWhere = new CrudDefinitionExample();
        com.raddle.crud.model.toolgen.CrudDefinitionExample.Criteria criteria = addWhere.createCriteria();
        criteria.andDeletedEqualTo((short) 0);
        criteria.andTableNameEqualTo(crudDefinition.getTableName());
        criteria.andDefTypeEqualTo(DefType.ADD.name());
        List<CrudDefinition> addList = crudDefinitionDao.selectByExample(addWhere);
        if (addList.size() > 0) {
            model.put("defAdd", addList.get(0));
        }
    }

    private List<CrudItem> queryDefListItems(CrudDefinition crudDefinition) {
        CrudItemExample where = new CrudItemExample();
        Criteria criteria = where.createCriteria();
        criteria.andDeletedEqualTo((short) 0);
        criteria.andCrudDefIdEqualTo(crudDefinition.getId());
        criteria.andFkTypeEqualTo(ItemFkType.DEF_LIST.name());
        where.setOrderByClause("item_order");
        List<CrudItem> defItems = crudItemDao.selectByExample(where);
        return defItems;
    }

    private String getReadSql(CrudDefinition crudDefinition) {
        if (StringUtils.isBlank(crudDefinition.getTableName()) && StringUtils.isBlank(crudDefinition.getReadSql())) {
            throw new RuntimeException("表名和读取sql必须有一项不为空");
        }
        String readSql = crudDefinition.getReadSql();
        if (StringUtils.isBlank(readSql) && !DefType.ADD.name().equals(crudDefinition.getDefType())) {
            readSql = dynamicFormManager.generateDynamicSelectSql(crudDefinition.getTableName(), datasourceManager.getDatasource(crudDefinition.getCrudDsId()));
        }
        return readSql;
    }

    @RequestMapping(value = "form/save")
    public String saveForm(Long defId, ModelMap model, HttpServletResponse response, HttpServletRequest request) throws IOException {
        CommonResult<Object> result = new CommonResult<Object>(false);
        try {
            if (defId == null) {
                throw new RuntimeException("表单id不能为空");
            }
            CrudDefinition crudDefinition = crudDefinitionDao.selectByPrimaryKey(defId);
            CrudDatasource crudDatasource = getDatasourceByEnvCode(crudDefinition, request);
            model.put("dsConfig", crudDatasource);
            String updateSql = getUpdateSql(crudDefinition);
            Map<String, Object> params = createParams(request, null);
            DataSource datasource = datasourceManager.getDatasource(crudDatasource.getId());
            if (crudDefinition.getDefType().equals(DefType.ADD.name()) && StringUtils.isNotBlank(crudDefinition.getKeySelectSql())) {
                Map<String, Object> insertKey = dynamicFormManager.queryForObject(crudDefinition.getKeySelectSql(), params, datasource);
                DynamicFormDao dynamicFormDao = new JdbcDynamicFormDao(datasource);
                TableInfo tableInfo = dynamicFormDao.getTableInfo(crudDefinition.getTableName());
                for (ColumnInfo columnInfo : tableInfo.getColumnInfos()) {
                    if (columnInfo.isPrimaryKey()) {
                        // 这种方式，只支持单主键的
                        params.put(columnInfo.getColumnName().toLowerCase(), insertKey.get("key"));
                        break;
                    }
                }
            }
            int count = dynamicFormManager.update(updateSql, params, datasource);
            result.setSuccess(true);
            result.setMessage("操作成功，影响条数[" + count + "]");
            return writeJson(result, response);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            result.setMessage("执行异常，" + e.getMessage());
            return writeJson(result, response);
        }
    }

    private String getUpdateSql(CrudDefinition crudDefinition) {
        if (StringUtils.isBlank(crudDefinition.getTableName()) && StringUtils.isBlank(crudDefinition.getUpdateSql())) {
            throw new RuntimeException("表名和更新sql必须有一项不为空");
        }
        String updateSql = crudDefinition.getUpdateSql();
        if (StringUtils.isBlank(updateSql)) {
            if (crudDefinition.getDefType().equals(DefType.ADD.name())) {
                updateSql = dynamicFormManager.generateDynamicInsertSql(crudDefinition.getTableName(), datasourceManager.getDatasource(crudDefinition.getCrudDsId()));
            }
            if (crudDefinition.getDefType().equals(DefType.EDIT.name())) {
                updateSql = dynamicFormManager.generateDynamicUpdateSql(crudDefinition.getTableName(), datasourceManager.getDatasource(crudDefinition.getCrudDsId()));
            }
            if (crudDefinition.getDefType().equals(DefType.DELETE.name())) {
                updateSql = dynamicFormManager.generateDynamicDeleteSql(crudDefinition.getTableName(), datasourceManager.getDatasource(crudDefinition.getCrudDsId()));
            }
        }
        return updateSql;
    }

    @SuppressWarnings("unchecked")
    private Map<String, Object> createParams(HttpServletRequest request, Map<String, Object> extraParams) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("request", request);
        Enumeration<String> parameterNames = request.getParameterNames();
        while (parameterNames != null && parameterNames.hasMoreElements()) {
            String name = parameterNames.nextElement();
            String[] values = request.getParameterValues(name);
            // 去掉空字符串
            if (values != null) {
                List<String> trimedValues = new ArrayList<String>();
                for (String value : values) {
                    if (StringUtils.isNotBlank(value)) {
                        trimedValues.add(value.trim());
                    }
                }
                if (trimedValues.size() > 0) {
                    if (trimedValues.size() == 1) {
                        params.put(name, trimedValues.get(0));
                    } else {
                        params.put(name, StringUtils.join(trimedValues, ","));
                    }
                }
            }
        }
        if (extraParams != null) {
            params.putAll(extraParams);
        }
        // 放一些utils类进去，方便模板渲染
        params.put("stringUtils", new StringUtils());
        params.put("stringEscapeUtils", new StringEscapeUtils());
        params.put("dateUtils", new DateUtils());
        params.put("dateFormatUtils", new DateFormatUtils());
        params.put("sqlUtils", new SqlUtils());
        return params;
    }
}
