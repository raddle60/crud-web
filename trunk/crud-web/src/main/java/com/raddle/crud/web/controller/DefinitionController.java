package com.raddle.crud.web.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.raddle.crud.biz.CrudDefinitionManager;
import com.raddle.crud.dao.CrudDatasourceDao;
import com.raddle.crud.dao.CrudDefinitionDao;
import com.raddle.crud.enums.DefType;
import com.raddle.crud.model.toolgen.CrudDatasource;
import com.raddle.crud.model.toolgen.CrudDatasourceExample;
import com.raddle.crud.model.toolgen.CrudDefinition;
import com.raddle.crud.model.toolgen.CrudDefinitionExample;

@Controller
public class DefinitionController extends BaseController {

    @Autowired
    private CrudDefinitionDao crudDefinitionDao;
    @Autowired
    private CrudDatasourceDao crudDatasourceDao;
    @Autowired
    private CrudDefinitionManager crudDefinitionManager;

    @RequestMapping(value = "def/def/list")
    public String list(ModelMap model, HttpServletResponse response, HttpServletRequest request) {
        CrudDefinitionExample example = new CrudDefinitionExample();
        example.createCriteria().andDeletedEqualTo((short) 0);
        example.setOrderByClause("created_at desc");
        List<CrudDefinition> list = crudDefinitionDao.selectByExample(example);
        model.put("list", list);
        model.put("defTypes", DefType.values());
        return "def/def/list";
    }

    @RequestMapping(value = "def/def/edit")
    public String edit(Long id, DefType defType, ModelMap model, HttpServletResponse response, HttpServletRequest request) {
        if (id != null) {
            CrudDefinition selectByPrimaryKey = crudDefinitionDao.selectByPrimaryKey(id);
            defType = DefType.valueOf(selectByPrimaryKey.getDefType());
            model.put("def", selectByPrimaryKey);
        } else {
            CrudDefinition def = new CrudDefinition();
            def.setDefType(defType.name());
            model.put("def", def);
        }
        if (defType == null) {
            throw new RuntimeException("表单类型不能为空");
        }
        CrudDatasourceExample example = new CrudDatasourceExample();
        example.createCriteria().andDeletedEqualTo((short) 0);
        example.setOrderByClause("created_at desc");
        List<CrudDatasource> datasources = crudDatasourceDao.selectByExample(example);
        model.put("datasources", datasources);
        return "def/def/edit";
    }

    @RequestMapping(value = "def/def/save")
    public String save(CrudDefinition def, ModelMap model, HttpServletResponse response, HttpServletRequest request) {
        CrudDefinition byCode = crudDefinitionManager.getCrudDefinitionByCode(def.getCode());
        if (def.getId() != null) {
            if (byCode != null && byCode.getId().longValue() != def.getId()) {
                model.put("message", "编码重复");
                return "common/new-window-result";
            }
            crudDefinitionDao.updateByPrimaryKeySelective(def);
        } else {
            if (byCode != null) {
                model.put("message", "编码重复");
                return "common/new-window-result";
            }
            def.setDeleted((short) 0);
            def.setCreatedAt(new Date());
            def.setUpdatedAt(new Date());
            crudDefinitionDao.insertSelective(def);
        }
        model.put("message", "保存成功");
        return "common/new-window-result";
    }

    @RequestMapping(value = "def/def/delete")
    public String delete(Long id, ModelMap model, HttpServletResponse response, HttpServletRequest request) {
        if (id != null) {
            CrudDefinition def = new CrudDefinition();
            def.setId(id);
            def.setDeleted((short) 1);
            crudDefinitionDao.updateByPrimaryKeySelective(def);
        }
        model.put("message", "删除成功");
        return "common/new-window-result";
    }

}
