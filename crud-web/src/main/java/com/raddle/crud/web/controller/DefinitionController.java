package com.raddle.crud.web.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.raddle.crud.biz.CrudDatasourceManager;
import com.raddle.crud.biz.CrudDefinitionManager;
import com.raddle.crud.dao.CrudDatasourceDao;
import com.raddle.crud.dao.CrudDefinitionDao;
import com.raddle.crud.dao.CrudItemDao;
import com.raddle.crud.enums.DefType;
import com.raddle.crud.enums.ItemCopyType;
import com.raddle.crud.enums.ItemFkType;
import com.raddle.crud.extdao.dbinfo.model.ColumnInfo;
import com.raddle.crud.extdao.dbinfo.model.TableInfo;
import com.raddle.crud.extdao.impl.JdbcDynamicFormDao;
import com.raddle.crud.model.toolgen.CrudDatasource;
import com.raddle.crud.model.toolgen.CrudDatasourceExample;
import com.raddle.crud.model.toolgen.CrudDefinition;
import com.raddle.crud.model.toolgen.CrudDefinitionExample;
import com.raddle.crud.model.toolgen.CrudDefinitionExample.Criteria;
import com.raddle.crud.model.toolgen.CrudItem;
import com.raddle.crud.model.toolgen.CrudItemExample;
import com.raddle.crud.vo.CommonResult;
import com.raddle.crud.vo.CrudDefinitionVo;
import com.raddle.crud.web.toolbox.DynamicFormTool;

@Controller
public class DefinitionController extends BaseController {

    private static final Pattern TABLE_SCHEMA_PATTERN = Pattern.compile("([^\\s]+)\\.([^\\s]+)");

    public static final Pattern COMP_DEF_ID_PATTERN = Pattern.compile("\\$!?\\{def_(\\d+)\\}");

    @Autowired
    private CrudDefinitionDao crudDefinitionDao;
    @Autowired
    private CrudDatasourceDao crudDatasourceDao;
    @Autowired
    private CrudDefinitionManager crudDefinitionManager;
    @Autowired
    private CrudDatasourceManager crudDatasourceManager;
    @Autowired
    private CrudItemDao crudItemDao;
    @Resource(name = "dynamicFormTool")
    private DynamicFormTool dynamicFormTool;

    @RequestMapping(value = "def/def/list")
    public String list(ModelMap model, HttpServletResponse response, HttpServletRequest request) {
        CrudDefinitionExample example = new CrudDefinitionExample();
        example.createCriteria().andDeletedEqualTo((short) 0);
        example.setOrderByClause("created_at desc");
        List<CrudDefinitionVo> list = crudDefinitionDao.selectCrudDefinitionVoByExample(example);
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
        // 校验参数
        if (DefType.MULTI_LIST.name().equals(def.getDefType())) {
            // 校验模板内容
            Matcher matcher = COMP_DEF_ID_PATTERN.matcher(def.getCompositeTemplate());
            while (matcher.find()) {
                Long defId = Long.parseLong(matcher.group(1));
                CrudDefinition subDef = crudDefinitionDao.selectByPrimaryKey(defId);
                if (subDef == null) {
                    model.put("message", "${def_" + defId + "}的模板不存在");
                    return "common/new-window-result";
                } else if (!DefType.LIST.name().equals(subDef.getDefType()) && !DefType.VIEW.name().equals(subDef.getDefType())) {
                    model.put("message", "${def_" + defId + "}的模板不是列表模板或查看");
                    return "common/new-window-result";
                }
            }
        }
        if (def.getId() != null) {
            crudDefinitionDao.updateByPrimaryKeySelective(def);
        } else {
            def.setDeleted((short) 0);
            def.setCreatedAt(new Date());
            def.setUpdatedAt(new Date());
            crudDefinitionDao.insertSelective(def);
        }
        model.put("message", "保存成功");
        return "common/new-window-result";
    }

    @RequestMapping(value = "def/def/batch-add")
    public String batchAdd(ModelMap model, HttpServletResponse response, HttpServletRequest request) {
        CrudDatasourceExample example = new CrudDatasourceExample();
        example.createCriteria().andDeletedEqualTo((short) 0);
        example.setOrderByClause("created_at desc");
        List<CrudDatasource> datasources = crudDatasourceDao.selectByExample(example);
        model.put("datasources", datasources);
        return "def/def/batch-add";
    }

    @RequestMapping(value = "def/def/batch-add-save")
    public String batchAddSave(DefType[] defTypes, Long crudDsId, String tables, ModelMap model, HttpServletResponse response, HttpServletRequest request) throws IOException {
        CommonResult<?> commonResult = new CommonResult<Object>(false);
        if (defTypes == null) {
            commonResult.setMessage("未选择新增类型");
            return writeJson(commonResult, response);
        }
        if (crudDsId == null) {
            commonResult.setMessage("未选择数据源");
            return writeJson(commonResult, response);
        }
        if (StringUtils.isEmpty(tables)) {
            commonResult.setMessage("未填写表列表");
            return writeJson(commonResult, response);
        }
        Matcher matcher = TABLE_SCHEMA_PATTERN.matcher(tables);
        DataSource dataSource = crudDatasourceManager.getDatasource(crudDsId);
        JdbcDynamicFormDao dynamicFormDao = new JdbcDynamicFormDao(dataSource);
        while (matcher.find()) {
            String tableSchema = matcher.group(1);
            String tableName = matcher.group(2);
            TableInfo tableInfo = dynamicFormDao.getTableInfo(tableSchema, tableName);
            if (tableInfo == null) {
                commonResult.setMessage("没获得到表结构信息" + matcher.group());
                return writeJson(commonResult, response);
            }
        }
        // 创建
        Matcher matcher2 = TABLE_SCHEMA_PATTERN.matcher(tables);
        List<String> inserted = new ArrayList<String>();
        List<String> skipped = new ArrayList<String>();
        while (matcher2.find()) {
            String tableSchema = matcher2.group(1);
            String tableName = matcher2.group(2);
            TableInfo tableInfo = dynamicFormDao.getTableInfo(tableSchema, tableName);
            for (DefType defType : defTypes) {
                // 检查是否存在
                if (!crudDefinitionManager.isExistByTable(defType, tableSchema, tableName)) {
                    CrudDefinition def = new CrudDefinition();
                    def.setName(tableName);
                    def.setDefType(defType.name());
                    def.setTableSchema(tableSchema);
                    def.setTableName(tableName);
                    def.setCrudDsId(crudDsId);
                    ItemFkType itemFkType = null;
                    if (defType == DefType.VIEW) {
                        List<String> whereCols = new ArrayList<String>();
                        Iterator<ColumnInfo> it = tableInfo.getPKColumnInfos().iterator();
                        while (it.hasNext()) {
                            ColumnInfo col = it.next();
                            whereCols.add("t." + col.getColumnName() + " = '${" + col.getColumnName().toLowerCase() + "}'");
                        }
                        def.setReadSql("select * from " + tableSchema + "." + tableName + " t where " + StringUtils.join(whereCols, " and "));
                        itemFkType = ItemFkType.DEF;
                    } else if (defType == DefType.LIST) {
                        def.setReadSql("select * from " + tableSchema + "." + tableName + " t where 1<>1 ");
                        itemFkType = ItemFkType.DEF_LIST;
                    } else {
                        skipped.add(tableSchema + "." + tableName + "-" + defType);
                        continue;
                    }
                    def.setDeleted((short) 0);
                    def.setCreatedAt(new Date());
                    def.setUpdatedAt(new Date());
                    inserted.add(tableSchema + "." + tableName + "-" + defType);
                    crudDefinitionDao.insertSelective(def);
                    crudDefinitionManager.autoCreateItemsByTable(def.getId(), itemFkType);
                } else {
                    skipped.add(tableSchema + "." + tableName + "-" + defType);
                }
            }
        }
        commonResult.setMessage("批量增加成功\n插入" + inserted.size() + "个\n" + StringUtils.join(inserted, "\n") + "\n已存在" + skipped.size() + "个\n" + StringUtils.join(skipped, "\n"));
        commonResult.setSuccess(inserted.size() > 0);
        return writeJson(commonResult, response);
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

    @RequestMapping(value = "def/def/auto-create-item")
    public String autoCreateItem(Long id, ItemFkType fkType, ModelMap model, HttpServletResponse response, HttpServletRequest request) {
        if (id == null) {
            throw new RuntimeException("表单id不能为空");
        }
        if (fkType == null) {
            throw new RuntimeException("外键类型不能为空");
        }
        // 根据表名，自动创建item
        CommonResult<?> commonResult = crudDefinitionManager.autoCreateItemsByTable(id, fkType);
        if (commonResult.isSuccess()) {
            model.put("message", "创建成功，已存在的不重复创建(包括已删除的)");
        } else {
            model.put("message", "创建失败，" + commonResult.getMessage());
        }
        return "common/new-window-result";
    }

    @RequestMapping(value = "def/def/copy-item-index")
    public String copyItemIndex(Long id, ItemFkType fkType, Long fromDefId, ItemCopyType[] selectedCopyTypes, ModelMap model, HttpServletResponse response, HttpServletRequest request) {
        if (id == null) {
            throw new RuntimeException("表单id不能为空");
        }
        if (fkType == null) {
            throw new RuntimeException("外键类型不能为空");
        }
        model.put("id", id);
        model.put("fkType", fkType);
        model.put("fromDefId", fromDefId);
        CrudDefinition def = crudDefinitionDao.selectByPrimaryKey(id);
        if (StringUtils.isBlank(def.getTableName())) {
            model.put("message", "表名为空，不能复制");
            return "common/new-window-result";
        }
        CrudDefinitionExample where = new CrudDefinitionExample();
        Criteria criteria = where.createCriteria();
        criteria.andTableNameEqualTo(def.getTableName());
        criteria.andDeletedEqualTo((short) 0);
        criteria.andIdNotEqualTo(id);
        List<CrudDefinition> defs = crudDefinitionDao.selectByExample(where);
        model.put("def", def);
        model.put("fromDefs", defs);
        if (fromDefId != null) {
            CrudItemExample itemWhere = new CrudItemExample();
            com.raddle.crud.model.toolgen.CrudItemExample.Criteria criteriaItem = itemWhere.createCriteria();
            criteriaItem.andDeletedEqualTo((short) 0);
            criteriaItem.andCrudDefIdEqualTo(fromDefId);
            criteriaItem.andFkTypeIn(Arrays.asList(new String[] { ItemFkType.DEF.name(), ItemFkType.DEF_LIST.name() }));
            itemWhere.setOrderByClause("fk_type,item_order");
            List<CrudItem> items = crudItemDao.selectByExample(itemWhere);
            model.put("items", items);
        }
        model.put("copyTypes", ItemCopyType.values());
        model.put("selectedCopyTypes", StringUtils.join(selectedCopyTypes, ","));
        model.put("formTool", dynamicFormTool);
        return "def/def/copy-item-index";
    }

    @RequestMapping(value = "def/def/copy-item")
    public String copyItem(Long id, ItemFkType fkType, Long[] itemIds, ItemCopyType[] selectedCopyTypes, ModelMap model, HttpServletResponse response, HttpServletRequest request) throws IOException {
        if (id == null) {
            throw new RuntimeException("表单id不能为空");
        }
        if (fkType == null) {
            throw new RuntimeException("外键类型不能为空");
        }
        if (ArrayUtils.isEmpty(itemIds)) {
            throw new RuntimeException("源选项不能为空");
        }
        if (ArrayUtils.isEmpty(selectedCopyTypes)) {
            throw new RuntimeException("复制类型不能为空");
        }
        CommonResult<?> commonResult = new CommonResult<Object>(false);
        CrudItemExample itemWhere = new CrudItemExample();
        com.raddle.crud.model.toolgen.CrudItemExample.Criteria criteriaItem = itemWhere.createCriteria();
        criteriaItem.andDeletedEqualTo((short) 0);
        criteriaItem.andCrudDefIdEqualTo(id);
        criteriaItem.andFkTypeEqualTo(fkType.name());
        // 目标表单的items
        int insertCount = 0;
        int updateCount = 0;
        List<CrudItem> targetItems = crudItemDao.selectByExample(itemWhere);
        for (Long itemId : itemIds) {
            CrudItem sourceItem = crudItemDao.selectByPrimaryKey(itemId);
            boolean exist = false;
            Long existItemId = null;
            for (CrudItem crudItem : targetItems) {
                if (crudItem.getVarName().equals(sourceItem.getVarName())) {
                    exist = true;
                    existItemId = crudItem.getId();
                    break;
                }
            }
            if (exist) {
                CrudItem updateItem = new CrudItem();
                updateItem.setId(existItemId);
                // 存在就修改
                if (ArrayUtils.contains(selectedCopyTypes, ItemCopyType.TITLE)) {
                    updateItem.setTitle(sourceItem.getTitle());
                }
                if (ArrayUtils.contains(selectedCopyTypes, ItemCopyType.ORDER)) {
                    updateItem.setItemOrder(sourceItem.getItemOrder());
                }
                if (ArrayUtils.contains(selectedCopyTypes, ItemCopyType.TYPE)) {
                    updateItem.setItemType(sourceItem.getItemType());
                    updateItem.setFormat(sourceItem.getFormat());
                    updateItem.setWebChkRule(sourceItem.getWebChkRule());
                    updateItem.setInputType(sourceItem.getInputType());
                    updateItem.setInputSize(sourceItem.getInputSize());
                    updateItem.setOptionType(sourceItem.getOptionType());
                    updateItem.setOptionValue(sourceItem.getOptionValue());
                    updateItem.setActionType(sourceItem.getActionType());
                    updateItem.setHref(sourceItem.getHref());
                    updateItem.setDescript(sourceItem.getDescript());
                }
                if (ArrayUtils.contains(selectedCopyTypes, ItemCopyType.OPTION)) {
                    updateItem.setOptionType(sourceItem.getOptionType());
                    updateItem.setOptionValue(sourceItem.getOptionValue());
                    updateItem.setCrudDsId(sourceItem.getCrudDsId());
                }
                crudItemDao.updateByPrimaryKeySelective(updateItem);
                updateCount++;
            } else {
                // 不存在，直接插入
                CrudItem newItem = new CrudItem();
                BeanUtils.copyProperties(sourceItem, newItem);
                newItem.setId(null);
                newItem.setCrudDefId(id);
                newItem.setFkType(fkType.name());
                crudItemDao.insert(newItem);
                insertCount++;
            }
        }
        commonResult.setSuccess(true);
        commonResult.setMessage("复制成功,插入" + insertCount + "条,更新" + updateCount + "条");
        return writeJson(commonResult, response);
    }
}
