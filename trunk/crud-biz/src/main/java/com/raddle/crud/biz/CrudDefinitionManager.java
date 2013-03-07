package com.raddle.crud.biz;

import com.raddle.crud.model.toolgen.CrudDefinition;
import com.raddle.crud.vo.CommonResult;

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

    /**
     * 根据表列名，生成列<br>
     * 已存在的不生成，包括已删除的
     * @param id
     * @return
     */
    public CommonResult<?> autoCreateItemsByTable(Long id);
}
