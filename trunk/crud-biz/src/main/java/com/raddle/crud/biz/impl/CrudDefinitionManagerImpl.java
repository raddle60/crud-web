package com.raddle.crud.biz.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.raddle.crud.biz.CrudDefinitionManager;
import com.raddle.crud.dao.CrudDefinitionDao;
import com.raddle.crud.model.toolgen.CrudDefinition;
import com.raddle.crud.model.toolgen.CrudDefinitionExample;
import com.raddle.crud.model.toolgen.CrudDefinitionExample.Criteria;

/**
 * 类CrudDefinitionManagerImpl.java的实现描述：
 * @author raddle60 2013-3-5 下午10:07:51
 */
@Service("crudDefinitionManager")
public class CrudDefinitionManagerImpl implements CrudDefinitionManager {

    @Autowired
    private CrudDefinitionDao crudDefinitionDao;

    @Override
    public CrudDefinition getCrudDefinitionByCode(String code) {
        CrudDefinitionExample example = new CrudDefinitionExample();
        Criteria criteria = example.createCriteria();
        criteria.andDeletedEqualTo((short) 0);
        criteria.andCodeEqualTo(code);
        List<CrudDefinition> list = crudDefinitionDao.selectByExample(example);
        if (list.size() > 0) {
            return list.get(0);
        }
        return null;
    }
}