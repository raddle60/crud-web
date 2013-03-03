package com.raddle.crud.dao.toolgen;

import com.raddle.crud.model.toolgen.CrudDatasource;
import com.raddle.crud.model.toolgen.CrudDatasourceExample;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.Update;

public interface CrudDatasourceMapper {
    int countByExample(CrudDatasourceExample example);

    int deleteByExample(CrudDatasourceExample example);

    @Delete({
        "delete from CRUD_DATASOURCE",
        "where ID = #{id,jdbcType=DECIMAL}"
    })
    int deleteByPrimaryKey(Long id);

    @Insert({
        "insert into CRUD_DATASOURCE (ID, DIRVER_CLASS_NAME, ",
        "URL, USERNAME, PASSWORD, ",
        "DELETED, CREATED_AT, ",
        "UPDATED_AT)",
        "values (#{id,jdbcType=DECIMAL}, #{dirverClassName,jdbcType=VARCHAR}, ",
        "#{url,jdbcType=VARCHAR}, #{username,jdbcType=VARCHAR}, #{password,jdbcType=VARCHAR}, ",
        "#{deleted,jdbcType=DECIMAL}, #{createdAt,jdbcType=TIMESTAMP}, ",
        "#{updatedAt,jdbcType=TIMESTAMP})"
    })
    @SelectKey(statement="SELECT SEQ_CRUD_DATASOURCE.NEXTVAL FROM DUAL", keyProperty="id", before=true, resultType=Long.class)
    int insert(CrudDatasource record);

    int insertSelective(CrudDatasource record);

    List<CrudDatasource> selectByExample(CrudDatasourceExample example);

    @Select({
        "select",
        "ID, DIRVER_CLASS_NAME, URL, USERNAME, PASSWORD, DELETED, CREATED_AT, UPDATED_AT",
        "from CRUD_DATASOURCE",
        "where ID = #{id,jdbcType=DECIMAL}"
    })
    @ResultMap("BaseResultMap")
    CrudDatasource selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") CrudDatasource record, @Param("example") CrudDatasourceExample example);

    int updateByExample(@Param("record") CrudDatasource record, @Param("example") CrudDatasourceExample example);

    int updateByPrimaryKeySelective(CrudDatasource record);

    @Update({
        "update CRUD_DATASOURCE",
        "set DIRVER_CLASS_NAME = #{dirverClassName,jdbcType=VARCHAR},",
          "URL = #{url,jdbcType=VARCHAR},",
          "USERNAME = #{username,jdbcType=VARCHAR},",
          "PASSWORD = #{password,jdbcType=VARCHAR},",
          "DELETED = #{deleted,jdbcType=DECIMAL},",
          "CREATED_AT = #{createdAt,jdbcType=TIMESTAMP},",
          "UPDATED_AT = #{updatedAt,jdbcType=TIMESTAMP}",
        "where ID = #{id,jdbcType=DECIMAL}"
    })
    int updateByPrimaryKey(CrudDatasource record);
}