package com.raddle.crud.dao;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;

import com.raddle.crud.dao.annotation.SqlMapper;
import com.raddle.crud.dao.toolgen.CrudItemMapper;

/**
 * 类CrudDatasourceDao.java的实现描述：dao
 * @author raddle60 2013-3-3 下午1:37:22
 */
@SqlMapper
public interface CrudItemDao extends CrudItemMapper {
    @Select({
        "select max(ITEM_ORDER)",
        "from CRUD_ITEM",
        "where DELETED = 0 and CRUD_DEF_ID = #{defId} and FK_TYPE = #{fkType}"
    })
    @ResultType(Integer.class)
    Integer selectMaxOrder(@Param("defId") Long defId, @Param("fkType") String fkType);
}
