package com.raddle.crud.web.controller;

import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.raddle.crud.biz.CrudDatasourceManager;
import com.raddle.crud.biz.DynamicFormManager;
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
import com.raddle.crud.model.toolgen.CrudDefinition;
import com.raddle.crud.model.toolgen.CrudDefinitionExample;
import com.raddle.crud.model.toolgen.CrudItem;
import com.raddle.crud.model.toolgen.CrudItemExample;
import com.raddle.crud.model.toolgen.CrudItemExample.Criteria;

/**
 * 类FormController.java的实现描述：表单页面
 * @author raddle60 2013-3-9 下午3:41:30
 */
@Controller
public class FormController extends BaseController {

    @Autowired
    private CrudDefinitionDao crudDefinitionDao;

    @Autowired
    private CrudItemDao crudItemDao;

    @Autowired
    private CrudDatasourceManager datasourceManager;

    @Autowired
    private DynamicFormManager dynamicFormManager;

    @RequestMapping(value = "form/show")
    public String showForm(Long defId, ModelMap model, HttpServletResponse response, HttpServletRequest request) {
        if (defId == null) {
            throw new RuntimeException("表单id不能为空");
        }
        CrudDefinition crudDefinition = crudDefinitionDao.selectByPrimaryKey(defId);
        String readSql = getReadSql(crudDefinition);
        if (crudDefinition.getDefType().equals(DefType.LIST.name())) {
            return toListResult(crudDefinition, readSql, model, request);
        } else {
            return toSingleResult(crudDefinition, readSql, model, request);
        }
    }

    private String toSingleResult(CrudDefinition crudDefinition, String readSql, ModelMap model, HttpServletRequest request) {
        if (StringUtils.isNotBlank(readSql)) {
            Object result = dynamicFormManager.queryForObject(readSql, createParams(request), datasourceManager.getDatasource(crudDefinition.getCrudDsId()));
            model.put("result", result);
        }
        List<CrudItem> defItems = queryDefItems(crudDefinition);
        model.put("defItems", defItems);
        model.put("def", crudDefinition);
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

    private String toListResult(CrudDefinition crudDefinition, String readSql, ModelMap model, HttpServletRequest request) {
        Object result = dynamicFormManager.queryForList(readSql, createParams(request), datasourceManager.getDatasource(crudDefinition.getCrudDsId()));
        model.put("result", result);
        List<CrudItem> whereItems = queryDefItems(crudDefinition);
        model.put("defWheres", whereItems);
        List<CrudItem> defListItems = queryDefListItems(crudDefinition);
        if (new Short((short) 1).equals(crudDefinition.getAutoMatchBtn()) && StringUtils.isNotBlank(crudDefinition.getTableName())) {
            putAddDef(crudDefinition, model);
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
                    href.append(request.getContextPath() + "/form/show?defId=" + updateDef.getId());
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
        model.put("defCols", defListItems);
        model.put("def", crudDefinition);
        model.put("params", createParams(request));
        return "form/list";
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
    public String saveForm(Long defId, ModelMap model, HttpServletResponse response, HttpServletRequest request) {
        if (defId == null) {
            throw new RuntimeException("表单id不能为空");
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    private Map<String, Object> createParams(HttpServletRequest request) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("request", request);
        Enumeration<String> attributeNames = request.getAttributeNames();
        while (attributeNames != null && attributeNames.hasMoreElements()) {
            String name = attributeNames.nextElement();
            String[] values = request.getParameterValues(name);
            if (values != null && values.length > 0) {
                if (values.length == 0) {
                    params.put(name, values[0]);
                } else {
                    params.put(name, values);
                }
            }
        }
        return params;
    }
}
