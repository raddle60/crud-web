package com.raddle.crud.web.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import com.raddle.crud.dao.CrudDatasourceDao;
import com.raddle.crud.model.toolgen.CrudDatasource;
import com.raddle.crud.model.toolgen.CrudDatasourceExample;

@Controller
public class DatasourceController extends BaseController {

    @Autowired
    private CrudDatasourceDao crudDatasourceDao;

    @RequestMapping(value = "def/datasource/list")
    public String list(ModelMap model, HttpServletResponse response, HttpServletRequest request) {
        CrudDatasourceExample example = new CrudDatasourceExample();
        example.createCriteria().andDeletedEqualTo((short) 0);
        example.setOrderByClause("created_at desc");
        List<CrudDatasource> list = crudDatasourceDao.selectByExample(example);
        model.put("list", list);
        return "def/datasource/list";
    }

    @RequestMapping(value = "def/datasource/edit")
    public String edit(Long id, ModelMap model, HttpServletResponse response, HttpServletRequest request) {
        if (id != null) {
            model.put("datasource", crudDatasourceDao.selectByPrimaryKey(id));
        }
        return "def/datasource/edit";
    }

    @RequestMapping(value = "def/datasource/save")
    public String save(CrudDatasource datasource, ModelMap model, HttpServletResponse response, HttpServletRequest request) {
        if (StringUtils.isEmpty(datasource.getPassword())) {
            datasource.setPassword(null);
        }
        if (datasource.getId() != null) {
            crudDatasourceDao.updateByPrimaryKeySelective(datasource);
        } else {
            datasource.setDeleted((short) 0);
            datasource.setCreatedAt(new Date());
            datasource.setUpdatedAt(new Date());
            crudDatasourceDao.insertSelective(datasource);
        }
        model.put("message", "保存成功");
        return "common/new-window-result";
    }

    @RequestMapping(value = "def/datasource/delete")
    public String delete(Long id, ModelMap model, HttpServletResponse response, HttpServletRequest request) {
        if (id != null) {
            CrudDatasource datasource = new CrudDatasource();
            datasource.setId(id);
            datasource.setDeleted((short) 1);
            crudDatasourceDao.updateByPrimaryKeySelective(datasource);
        }
        model.put("message", "删除成功");
        return "common/new-window-result";
    }
}
