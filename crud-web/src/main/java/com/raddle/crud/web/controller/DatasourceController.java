package com.raddle.crud.web.controller;

import java.sql.Connection;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.raddle.crud.dao.CrudDatasourceDao;
import com.raddle.crud.extdao.impl.JdbcDynamicFormDao;
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

    @RequestMapping(value = "def/datasource/test-connection")
    public String testConn(Long id, ModelMap model, HttpServletResponse response, HttpServletRequest request) {
        CrudDatasource datasource = crudDatasourceDao.selectByPrimaryKey(id);
        try {
            DriverManagerDataSource ds = new DriverManagerDataSource();
            ds.setDriverClassName(datasource.getDirverClassName());
            ds.setUrl(datasource.getUrl());
            ds.setUsername(datasource.getUsername());
            ds.setPassword(datasource.getPassword());
            Connection connection = ds.getConnection();
            connection.close();
            model.put("message", "连接成功");
        } catch (Exception e) {
            model.put("message", "连接失败, " + e.getMessage());
        }

        return "common/new-window-result";
    }
    
    @RequestMapping(value = "def/datasource/clear-table-cache")
    public String clearTableCache(ModelMap model, HttpServletResponse response, HttpServletRequest request) {
        JdbcDynamicFormDao.clearCache();
        model.put("message", "清除缓存成功");
        return "common/new-window-result";
    }
}
