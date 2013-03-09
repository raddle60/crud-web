package com.raddle.crud.web.controller;

import java.util.Enumeration;
import java.util.HashMap;
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
import com.raddle.crud.enums.DefType;
import com.raddle.crud.enums.ItemFkType;
import com.raddle.crud.model.toolgen.CrudDefinition;
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
        Object result = dynamicFormManager.queryForObject(readSql, createParams(request), datasourceManager.getDatasource(crudDefinition.getCrudDsId()));
        model.put("result", result);
        CrudItemExample where = new CrudItemExample();
        Criteria criteria = where.createCriteria();
        criteria.andDeletedEqualTo((short) 0);
        criteria.andCrudDefIdEqualTo(crudDefinition.getId());
        criteria.andFkTypeEqualTo(ItemFkType.DEF.name());
        where.setOrderByClause("item_order");
        model.put("defItems", crudItemDao.selectByExample(where));
        model.put("def", crudDefinition);
        return "form/show";
    }

    private String toListResult(CrudDefinition crudDefinition, String readSql, ModelMap model, HttpServletRequest request) {
        Object result = dynamicFormManager.queryForList(readSql, createParams(request), datasourceManager.getDatasource(crudDefinition.getCrudDsId()));
        model.put("result", result);
        CrudItemExample where = new CrudItemExample();
        Criteria criteria = where.createCriteria();
        criteria.andDeletedEqualTo((short) 0);
        criteria.andCrudDefIdEqualTo(crudDefinition.getId());
        criteria.andFkTypeEqualTo(ItemFkType.DEF.name());
        where.setOrderByClause("item_order");
        model.put("defWheres", crudItemDao.selectByExample(where));
        CrudItemExample whereCols = new CrudItemExample();
        Criteria criteriaCols = whereCols.createCriteria();
        criteriaCols.andDeletedEqualTo((short) 0);
        criteriaCols.andCrudDefIdEqualTo(crudDefinition.getId());
        criteriaCols.andFkTypeEqualTo(ItemFkType.DEF_LIST.name());
        whereCols.setOrderByClause("item_order");
        model.put("defCols", crudItemDao.selectByExample(whereCols));
        model.put("def", crudDefinition);
        model.put("params", createParams(request));
        return "form/list";
    }

    private String getReadSql(CrudDefinition crudDefinition) {
        if (StringUtils.isBlank(crudDefinition.getTableName()) && StringUtils.isBlank(crudDefinition.getReadSql())) {
            throw new RuntimeException("表名和读取sql必须有一项不为空");
        }
        String readSql = crudDefinition.getReadSql();
        if (StringUtils.isBlank(readSql)) {
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
