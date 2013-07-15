package com.raddle.crud.web.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.raddle.crud.dao.CrudDatasourceDao;
import com.raddle.crud.dao.CrudDefinitionDao;
import com.raddle.crud.dao.CrudItemDao;
import com.raddle.crud.enums.ActionType;
import com.raddle.crud.enums.DefType;
import com.raddle.crud.enums.InputType;
import com.raddle.crud.enums.ItemFkType;
import com.raddle.crud.enums.ItemType;
import com.raddle.crud.enums.OptionType;
import com.raddle.crud.model.toolgen.CrudDatasource;
import com.raddle.crud.model.toolgen.CrudDatasourceExample;
import com.raddle.crud.model.toolgen.CrudDefinition;
import com.raddle.crud.model.toolgen.CrudItem;
import com.raddle.crud.model.toolgen.CrudItemExample;
import com.raddle.crud.model.toolgen.CrudItemExample.Criteria;

@Controller
public class ItemController extends BaseController {

    @Autowired
    private CrudItemDao crudItemDao;
    @Autowired
    private CrudDatasourceDao crudDatasourceDao;
    @Autowired
    private CrudDefinitionDao crudDefinitionDao;

    @RequestMapping(value = "def/item/list")
    public String list(Long defId, ItemFkType fkType, ModelMap model, HttpServletResponse response, HttpServletRequest request) {
        if (defId == null) {
            throw new RuntimeException("表单id不能为空");
        }
        if (fkType == null) {
            throw new RuntimeException("外键类型不能为空");
        }
        CrudItemExample example = new CrudItemExample();
        Criteria criteria = example.createCriteria();
        criteria.andCrudDefIdEqualTo(defId);
        criteria.andFkTypeEqualTo(fkType.name());
        example.setOrderByClause("deleted,item_order");
        List<CrudItem> list = crudItemDao.selectByExample(example);
        model.put("list", list);
        model.put("defId", defId);
        model.put("fkType", fkType.name());
        // 表单
        CrudDefinition crudDefinition = crudDefinitionDao.selectByPrimaryKey(defId);
        model.put("def", crudDefinition);
        if (crudDefinition.getDefType().equals(DefType.LIST.name())) {
            if (fkType == ItemFkType.DEF) {
                model.put("listTitle", "条件");
            } else {
                model.put("listTitle", "显示列");
            }
        } else {
            model.put("listTitle", "表单项");
        }
        return "def/item/list";
    }

    @RequestMapping(value = "def/item/edit")
    public String edit(Long id, Long defId, ItemFkType fkType, ModelMap model, HttpServletResponse response, HttpServletRequest request) {
        if (id != null) {
            CrudItem selectByPrimaryKey = crudItemDao.selectByPrimaryKey(id);
            model.put("item", selectByPrimaryKey);
        } else {
            if (defId == null) {
                throw new RuntimeException("表单id不能为空");
            }
            if (fkType == null) {
                throw new RuntimeException("外键类型不能为空");
            }
            CrudItem item = new CrudItem();
            item.setCrudDefId(defId);
            item.setFkType(fkType.name());
            model.put("item", item);
        }
        CrudDatasourceExample example = new CrudDatasourceExample();
        example.createCriteria().andDeletedEqualTo((short) 0);
        example.setOrderByClause("created_at desc");
        List<CrudDatasource> datasources = crudDatasourceDao.selectByExample(example);
        model.put("datasources", datasources);
        model.put("itemTypes", ItemType.values());
        model.put("inputTypes", InputType.values());
        model.put("optionTypes", OptionType.values());
        model.put("actionTypes", ActionType.values());
        return "def/item/edit";
    }

    @RequestMapping(value = "def/item/save")
    public String save(CrudItem item, ModelMap model, HttpServletResponse response, HttpServletRequest request) {
        if (item.getCrudDefId() == null) {
            throw new RuntimeException("表单id不能为空");
        }
        if (item.getFkType() == null || ItemFkType.valueOf(item.getFkType()) == null) {
            throw new RuntimeException("外键类型不能为空");
        }
        item.setVarName(StringUtils.lowerCase(item.getVarName()));
        if (item.getId() != null) {
            crudItemDao.updateByPrimaryKeySelective(item);
        } else {
            item.setDeleted((short) 0);
            item.setCreatedAt(new Date());
            item.setUpdatedAt(new Date());
            // 获取排序值
            if (item.getItemOrder() == null) {
                Integer selectMaxOrder = crudItemDao.selectMaxOrder(item.getCrudDefId(), item.getFkType());
                if (selectMaxOrder == null) {
                    item.setItemOrder(1);
                } else {
                    item.setItemOrder(selectMaxOrder + 1);
                }
            }
            crudItemDao.insertSelective(item);
        }
        model.put("message", "保存成功");
        return "common/new-window-result";
    }

    @RequestMapping(value = "def/item/delete")
    public String delete(Long id, ModelMap model, HttpServletResponse response, HttpServletRequest request) {
        if (id != null) {
            CrudItem item = new CrudItem();
            item.setId(id);
            item.setDeleted((short) 1);
            crudItemDao.updateByPrimaryKeySelective(item);
        }
        model.put("message", "删除成功");
        return "common/new-window-result";
    }

    @RequestMapping(value = "def/item/restore")
    public String restore(Long id, ModelMap model, HttpServletResponse response, HttpServletRequest request) {
        if (id != null) {
            CrudItem item = new CrudItem();
            item.setId(id);
            item.setDeleted((short) 0);
            crudItemDao.updateByPrimaryKeySelective(item);
        }
        model.put("message", "恢复成功");
        return "common/new-window-result";
    }

}
