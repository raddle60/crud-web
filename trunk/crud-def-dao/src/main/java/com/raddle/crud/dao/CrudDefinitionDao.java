package com.raddle.crud.dao;

import java.util.List;

import com.raddle.crud.dao.annotation.SqlMapper;
import com.raddle.crud.dao.toolgen.CrudDefinitionMapper;
import com.raddle.crud.model.toolgen.CrudDefinitionExample;
import com.raddle.crud.vo.CrudDefinitionVo;

/**
 * 类CrudDatasourceDao.java的实现描述：dao
 * @author raddle60 2013-3-3 下午1:37:22
 */
@SqlMapper
public interface CrudDefinitionDao extends CrudDefinitionMapper {

    List<CrudDefinitionVo> selectCrudDefinitionVoByExample(CrudDefinitionExample example);
}
