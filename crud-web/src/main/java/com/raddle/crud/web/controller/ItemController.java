package com.raddle.crud.web.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.raddle.crud.biz.CrudDatasourceManager;
import com.raddle.crud.biz.CrudDefinitionManager;
import com.raddle.crud.dao.CrudDatasourceDao;
import com.raddle.crud.dao.CrudDefinitionDao;
import com.raddle.crud.dao.CrudItemDao;
import com.raddle.crud.enums.ActionType;
import com.raddle.crud.enums.DefType;
import com.raddle.crud.enums.InputType;
import com.raddle.crud.enums.ItemFkType;
import com.raddle.crud.enums.ItemType;
import com.raddle.crud.enums.OptionType;
import com.raddle.crud.extdao.dbinfo.model.ColumnInfo;
import com.raddle.crud.extdao.dbinfo.model.TableInfo;
import com.raddle.crud.extdao.impl.JdbcDynamicFormDao;
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
    @Autowired
    private CrudDefinitionManager crudDefinitionManager;
    @Autowired
    private CrudDatasourceManager crudDatasourceManager;

    @Resource(name = "crudTransactionManager")
    private PlatformTransactionManager transactionManager;

    @RequestMapping(value = "def/item/list")
    public String list(Long defId, ItemFkType fkType, ModelMap model, HttpServletResponse response,
            HttpServletRequest request) {
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
    public String edit(Long id, Long defId, ItemFkType fkType, ModelMap model, HttpServletResponse response,
            HttpServletRequest request) {
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
    public String save(final CrudItem item, ModelMap model, HttpServletResponse response, HttpServletRequest request) {
        if (item.getCrudDefId() == null) {
            throw new RuntimeException("表单id不能为空");
        }
        if (item.getFkType() == null || ItemFkType.valueOf(item.getFkType()) == null) {
            throw new RuntimeException("外键类型不能为空");
        }
        item.setVarName(StringUtils.lowerCase(item.getVarName()));
        if (item.getId() != null) {
            CrudItem crudItem = crudItemDao.selectByPrimaryKey(item.getId());
            if (crudItem.getItemOrder().equals(item.getItemOrder())) {
                // 排序相等,不需要调整
                crudItemDao.updateByPrimaryKeySelective(item);
            } else {
                // 排序不等,需要调整
                CrudItemExample existExample = new CrudItemExample();
                Criteria existWhere = existExample.createCriteria();
                existWhere.andCrudDefIdEqualTo(item.getCrudDefId());
                existWhere.andFkTypeEqualTo(item.getFkType());
                existWhere.andItemOrderEqualTo(item.getItemOrder());
                List<CrudItem> existList = crudItemDao.selectByExample(existExample);
                if (existList.size() == 0) {
                    // 新排序没被占用,直接使用
                    crudItemDao.updateByPrimaryKeySelective(item);
                } else {
                    // 新排序被占用了,需要往下调
                    TransactionTemplate transactionTemplate = new TransactionTemplate(transactionManager);
                    transactionTemplate.execute(new TransactionCallback<Object>() {

                        @Override
                        public Object doInTransaction(TransactionStatus status) {
                            crudItemDao.downItemOrder(item.getCrudDefId(), item.getFkType(), item.getItemOrder());
                            crudItemDao.updateByPrimaryKeySelective(item);
                            return null;
                        }
                    });
                }
            }
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
    
    @RequestMapping(value = "def/item/setOrder")
    public String setOrder(Long id, final Integer itemOrder, ModelMap model, HttpServletResponse response, HttpServletRequest request) {
        if (id == null) {
            throw new RuntimeException("表单项id不能为空");
        }
        if (itemOrder == null) {
            throw new RuntimeException("顺序号不能为空");
        }
        final CrudItem crudItem = crudItemDao.selectByPrimaryKey(id);
        final CrudItem item = new CrudItem();
        item.setId(id);
        item.setItemOrder(itemOrder);
        if (crudItem.getItemOrder().equals(itemOrder)) {
            // 排序相等,不需要调整
            crudItemDao.updateByPrimaryKeySelective(item);
        } else {
            // 排序不等,需要调整
            CrudItemExample existExample = new CrudItemExample();
            Criteria existWhere = existExample.createCriteria();
            existWhere.andCrudDefIdEqualTo(crudItem.getCrudDefId());
            existWhere.andFkTypeEqualTo(crudItem.getFkType());
            existWhere.andItemOrderEqualTo(crudItem.getItemOrder());
            List<CrudItem> existList = crudItemDao.selectByExample(existExample);
            if (existList.size() == 0) {
                // 新排序没被占用,直接使用
                crudItemDao.updateByPrimaryKeySelective(item);
            } else {
                // 新排序被占用了,需要往下调
                TransactionTemplate transactionTemplate = new TransactionTemplate(transactionManager);
                transactionTemplate.execute(new TransactionCallback<Object>() {

                    @Override
                    public Object doInTransaction(TransactionStatus status) {
                        crudItemDao.downItemOrder(crudItem.getCrudDefId(), crudItem.getFkType(), itemOrder);
                        crudItemDao.updateByPrimaryKeySelective(item);
                        return null;
                    }
                });
            }
        }
        return "common/new-window-result";
    }

    @RequestMapping(value = "def/item/setTitle")
    public String setTitle(Long id, ModelMap model, HttpServletResponse response, HttpServletRequest request) {
        if (id == null) {
            throw new RuntimeException("表单项id不能为空");
        }
        String title = null;
        Matcher matcher = Pattern.compile("title=([%\\w]+)").matcher(request.getQueryString());
        if (matcher.find()) {
            new URLDecoder();
            try {
                title = URLDecoder.decode(matcher.group(1), "utf-8");
            } catch (UnsupportedEncodingException e) {
            }
        }
        if (StringUtils.isEmpty(title)) {
            throw new RuntimeException("标题不能为空");
        }
        final CrudItem item = new CrudItem();
        item.setId(id);
        item.setTitle(title);
        crudItemDao.updateByPrimaryKeySelective(item);
        return "common/new-window-result";
    }
    
    @RequestMapping(value = "def/item/match-form")
    public String matchForm(Long defId, ItemFkType fkType, DefType defType, ModelMap model, HttpServletResponse response, HttpServletRequest request) {
        if (defId == null) {
            throw new RuntimeException("表单id不能为空");
        }
        if (fkType == null) {
            throw new RuntimeException("fkType不能为空");
        }
        if (defType == null) {
            throw new RuntimeException("defType不能为空");
        }
        CrudDefinition crudDefinition = crudDefinitionDao.selectByPrimaryKey(defId);
        List<CrudDefinition> byTable = crudDefinitionManager.getByTable(defType, crudDefinition.getTableSchema(), crudDefinition.getTableName());
        if (byTable.size() == 0) {
            model.put("message", "未找到" + crudDefinition.getTableSchema() + "." + crudDefinition.getTableName() + "-" + defType + "的表单");
        } else if (byTable.size() > 0) {
            CrudItemExample example = new CrudItemExample();
            Criteria criteria = example.createCriteria();
            criteria.andCrudDefIdEqualTo(defId);
            criteria.andFkTypeEqualTo(fkType.name());
            example.setOrderByClause("deleted,item_order");
            List<CrudItem> list = crudItemDao.selectByExample(example);
            //
            DataSource dataSource = crudDatasourceManager.getDatasource(crudDefinition.getCrudDsId());
            JdbcDynamicFormDao dynamicFormDao = new JdbcDynamicFormDao(dataSource);
            TableInfo tableInfo = dynamicFormDao.getTableInfo(crudDefinition.getTableSchema(), crudDefinition.getTableName());
            for (CrudItem crudItem : list) {
                Iterator<ColumnInfo> it = tableInfo.getPKColumnInfos().iterator();
                while (it.hasNext()) {
                    ColumnInfo col = it.next();
                    if (col.getColumnName().toLowerCase().equals(crudItem.getVarName())) {
                        CrudItem update = new CrudItem();
                        update.setId(crudItem.getId());
                        update.setItemType(ItemType.ACTION.name());
                        update.setActionType(ActionType.HREF.name());
                        Iterator<ColumnInfo> it2 = tableInfo.getPKColumnInfos().iterator();
                        List<String> whereCols = new ArrayList<String>();
                        while (it2.hasNext()) {
                            ColumnInfo col2 = it2.next();
                            whereCols.add(col2.getColumnName().toLowerCase() + "=${" + col2.getColumnName().toLowerCase() + "}");
                        }
                        update.setHref("/crud-web/form/show.htm?defId=" + byTable.get(0).getId() + "&" + StringUtils.join(whereCols, "&"));
                        crudItemDao.updateByPrimaryKeySelective(update);
                        break;
                    }
                }
            }
        }
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
        // model.put("message", "删除成功");
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
        // model.put("message", "恢复成功");
        return "common/new-window-result";
    }

}
