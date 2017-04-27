package com.raddle.crud.biz.impl;

import java.util.Date;

import javax.sql.DataSource;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.raddle.crud.biz.CrudDatasourceManager;
import com.raddle.crud.biz.CrudDefinitionManager;
import com.raddle.crud.dao.CrudDefinitionDao;
import com.raddle.crud.dao.CrudItemDao;
import com.raddle.crud.enums.DefType;
import com.raddle.crud.enums.InputType;
import com.raddle.crud.enums.ItemFkType;
import com.raddle.crud.enums.ItemType;
import com.raddle.crud.extdao.dbinfo.model.ColumnInfo;
import com.raddle.crud.extdao.dbinfo.model.TableInfo;
import com.raddle.crud.extdao.impl.JdbcDynamicFormDao;
import com.raddle.crud.model.toolgen.CrudDefinition;
import com.raddle.crud.model.toolgen.CrudItem;
import com.raddle.crud.model.toolgen.CrudItemExample;
import com.raddle.crud.vo.CommonResult;

/**
 * 类CrudDefinitionManagerImpl.java的实现描述：
 * @author raddle60 2013-3-5 下午10:07:51
 */
@Service("crudDefinitionManager")
public class CrudDefinitionManagerImpl implements CrudDefinitionManager {

    @Autowired
    private CrudDefinitionDao crudDefinitionDao;

    @Autowired
    private CrudDatasourceManager crudDatasourceManager;

    @Autowired
    private CrudItemDao crudItemDao;

    @Override
    @Transactional("crudTransactionManager")
    public CommonResult<?> autoCreateItemsByTable(Long id, ItemFkType fkType) {
        CrudDefinition crudDefinition = crudDefinitionDao.selectByPrimaryKey(id);
        if (StringUtils.isEmpty(crudDefinition.getTableName())) {
            return new CommonResult<Object>(false, "表名为空");
        }
        if (crudDefinition.getCrudDsId() == null) {
            return new CommonResult<Object>(false, "没选择数据源");
        }
        DataSource dataSource = crudDatasourceManager.getDatasource(crudDefinition.getCrudDsId());
        JdbcDynamicFormDao dynamicFormDao = new JdbcDynamicFormDao(dataSource);
        TableInfo tableInfo = dynamicFormDao.getTableInfo(crudDefinition.getTableSchema(), crudDefinition.getTableName());
        if (tableInfo == null) {
            return new CommonResult<Object>(false, "没获得到表结构信息");
        }
        int i = 0;
        for (ColumnInfo columnInfo : tableInfo.getColumnInfos()) {
            i++;
            CrudItemExample crudItemExample = new CrudItemExample();
            com.raddle.crud.model.toolgen.CrudItemExample.Criteria criteria = crudItemExample.createCriteria();
            criteria.andCrudDefIdEqualTo(id);
            criteria.andFkTypeEqualTo(fkType.name());
            criteria.andVarNameEqualTo(columnInfo.getColumnName().toLowerCase());
            int count = crudItemDao.countByExample(crudItemExample);
            if (count == 0) {
                // 创建表单项
                if (columnInfo.isPrimaryKey()) {
                    if (crudDefinition.getDefType().equals(DefType.ADD.name()) || crudDefinition.getDefType().equals(DefType.EDIT.name())) {
                        // 新增，编辑放入hidden
                        addItem(id, fkType, ItemType.HIDDEN, null, i, columnInfo);
                    } else if (crudDefinition.getDefType().equals(DefType.LIST.name()) && fkType == ItemFkType.DEF) {
                        // 查询条件是输入框
                        addItem(id, fkType, ItemType.INPUT, InputType.TEXT, i, columnInfo);
                    } else if (crudDefinition.getDefType().equals(DefType.DELETE.name())) {
                        // 删除，放一个hidden，放一个查看的
                        addItem(id, fkType, ItemType.HIDDEN, null, i, columnInfo);
                        i++;
                        addItem(id, fkType, ItemType.LABEL, null, i, columnInfo);
                    } else {
                        // 查看和列表是label
                        addItem(id, fkType, ItemType.LABEL, null, i, columnInfo);
                    }
                } else {
                    if (crudDefinition.getDefType().equals(DefType.ADD.name()) || crudDefinition.getDefType().equals(DefType.EDIT.name()) || (crudDefinition.getDefType().equals(DefType.LIST.name()) && fkType == ItemFkType.DEF)) {
                        // 新增，编辑，查询条件是输入框
                        addItem(id, fkType, ItemType.INPUT, InputType.TEXT, i, columnInfo);
                    } else {
                        // 查看，删除和列表是label
                        addItem(id, fkType, ItemType.LABEL, null, i, columnInfo);
                    }
                }
            }
        }
        return new CommonResult<Object>(true);
    }

    private void addItem(Long id, ItemFkType fkType, ItemType itemType, InputType inputType, int i, ColumnInfo columnInfo) {
        CrudItem item = new CrudItem();
        item.setCrudDefId(id);
        item.setFkType(fkType.name());
        item.setItemType(itemType.name());
        item.setInputType(inputType != null ? inputType.name() : null);
        item.setTitle(columnInfo.getColumnName().toLowerCase());
        item.setVarName(columnInfo.getColumnName().toLowerCase());
        item.setItemOrder(i);
        item.setDescript(columnInfo.getComment());
        item.setDeleted((short) 0);
        item.setCreatedAt(new Date());
        item.setUpdatedAt(new Date());
        crudItemDao.insertSelective(item);
    }
}
