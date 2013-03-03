package com.raddle.crud.dao.toolgen;

import com.raddle.crud.model.toolgen.CrudDefinition;
import com.raddle.crud.model.toolgen.CrudDefinitionExample;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.Update;

public interface CrudDefinitionMapper {
    int countByExample(CrudDefinitionExample example);

    int deleteByExample(CrudDefinitionExample example);

    @Delete({
        "delete from CRUD_DEFINITION",
        "where ID = #{id,jdbcType=DECIMAL}"
    })
    int deleteByPrimaryKey(Long id);

    @Insert({
        "insert into CRUD_DEFINITION (ID, CODE, ",
        "NAME, DEF_TYPE, TABLE_NAME, ",
        "CRUD_DS_ID, PRE_SQL, ",
        "SQL_TYPE, SQL, CREATE_TM_COL, ",
        "UPDATE_TM_COL, DELETED, ",
        "CREATED_AT, UPDATED_AT)",
        "values (#{id,jdbcType=DECIMAL}, #{code,jdbcType=VARCHAR}, ",
        "#{name,jdbcType=VARCHAR}, #{defType,jdbcType=VARCHAR}, #{tableName,jdbcType=VARCHAR}, ",
        "#{crudDsId,jdbcType=DECIMAL}, #{preSql,jdbcType=VARCHAR}, ",
        "#{sqlType,jdbcType=VARCHAR}, #{sql,jdbcType=VARCHAR}, #{createTmCol,jdbcType=VARCHAR}, ",
        "#{updateTmCol,jdbcType=VARCHAR}, #{deleted,jdbcType=DECIMAL}, ",
        "#{createdAt,jdbcType=TIMESTAMP}, #{updatedAt,jdbcType=TIMESTAMP})"
    })
    @SelectKey(statement="SELECT SEQ_CRUD_DEFINITION.NEXTVAL FROM DUAL", keyProperty="id", before=true, resultType=Long.class)
    int insert(CrudDefinition record);

    int insertSelective(CrudDefinition record);

    List<CrudDefinition> selectByExample(CrudDefinitionExample example);

    @Select({
        "select",
        "ID, CODE, NAME, DEF_TYPE, TABLE_NAME, CRUD_DS_ID, PRE_SQL, SQL_TYPE, SQL, CREATE_TM_COL, ",
        "UPDATE_TM_COL, DELETED, CREATED_AT, UPDATED_AT",
        "from CRUD_DEFINITION",
        "where ID = #{id,jdbcType=DECIMAL}"
    })
    @ResultMap("BaseResultMap")
    CrudDefinition selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") CrudDefinition record, @Param("example") CrudDefinitionExample example);

    int updateByExample(@Param("record") CrudDefinition record, @Param("example") CrudDefinitionExample example);

    int updateByPrimaryKeySelective(CrudDefinition record);

    @Update({
        "update CRUD_DEFINITION",
        "set CODE = #{code,jdbcType=VARCHAR},",
          "NAME = #{name,jdbcType=VARCHAR},",
          "DEF_TYPE = #{defType,jdbcType=VARCHAR},",
          "TABLE_NAME = #{tableName,jdbcType=VARCHAR},",
          "CRUD_DS_ID = #{crudDsId,jdbcType=DECIMAL},",
          "PRE_SQL = #{preSql,jdbcType=VARCHAR},",
          "SQL_TYPE = #{sqlType,jdbcType=VARCHAR},",
          "SQL = #{sql,jdbcType=VARCHAR},",
          "CREATE_TM_COL = #{createTmCol,jdbcType=VARCHAR},",
          "UPDATE_TM_COL = #{updateTmCol,jdbcType=VARCHAR},",
          "DELETED = #{deleted,jdbcType=DECIMAL},",
          "CREATED_AT = #{createdAt,jdbcType=TIMESTAMP},",
          "UPDATED_AT = #{updatedAt,jdbcType=TIMESTAMP}",
        "where ID = #{id,jdbcType=DECIMAL}"
    })
    int updateByPrimaryKey(CrudDefinition record);
}