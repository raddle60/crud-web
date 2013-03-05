package com.raddle.crud.biz;

import com.raddle.crud.model.toolgen.CrudDefinition;

/**
 * 类CrudDefinitionManager.java的实现描述：
 * @author raddle60 2013-3-5 下午10:04:28
 */
public interface CrudDefinitionManager {

    /**
     * 获得未删除的表单定义
     * @param code
     * @return
     */
    public CrudDefinition getCrudDefinitionByCode(String code);
}
