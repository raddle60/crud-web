package com.raddle.crud.dao.toolgen;

import com.raddle.crud.model.toolgen.CrudItem;
import com.raddle.crud.model.toolgen.CrudItemExample;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.Update;

public interface CrudItemMapper {
    int countByExample(CrudItemExample example);

    int deleteByExample(CrudItemExample example);

    @Delete({
        "delete from CRUD_ITEM",
        "where ID = #{id,jdbcType=DECIMAL}"
    })
    int deleteByPrimaryKey(Long id);

    @Insert({
        "insert into CRUD_ITEM (ID, CRUD_DEF_ID, ",
        "FK_TYPE, TITLE, VAR_NAME, ",
        "ITEM_TYPE, INPUT_TYPE, ",
        "ACTION_TYPE, HREF, ",
        "WEB_CHK_RULE, SERVER_CHK_RULE, ",
        "OPTION_TYPE, OPTION_VALUE, ",
        "CRUD_DS_ID, DELETED, ",
        "CREATED_AT, UPDATED_AT)",
        "values (#{id,jdbcType=DECIMAL}, #{crudDefId,jdbcType=DECIMAL}, ",
        "#{fkType,jdbcType=VARCHAR}, #{title,jdbcType=VARCHAR}, #{varName,jdbcType=VARCHAR}, ",
        "#{itemType,jdbcType=VARCHAR}, #{inputType,jdbcType=VARCHAR}, ",
        "#{actionType,jdbcType=VARCHAR}, #{href,jdbcType=VARCHAR}, ",
        "#{webChkRule,jdbcType=VARCHAR}, #{serverChkRule,jdbcType=VARCHAR}, ",
        "#{optionType,jdbcType=VARCHAR}, #{optionValue,jdbcType=VARCHAR}, ",
        "#{crudDsId,jdbcType=DECIMAL}, #{deleted,jdbcType=DECIMAL}, ",
        "#{createdAt,jdbcType=TIMESTAMP}, #{updatedAt,jdbcType=TIMESTAMP})"
    })
    @SelectKey(statement="SELECT SEQ_CRUD_ITEM.NEXTVAL FROM DUAL", keyProperty="id", before=true, resultType=Long.class)
    int insert(CrudItem record);

    int insertSelective(CrudItem record);

    List<CrudItem> selectByExample(CrudItemExample example);

    @Select({
        "select",
        "ID, CRUD_DEF_ID, FK_TYPE, TITLE, VAR_NAME, ITEM_TYPE, INPUT_TYPE, ACTION_TYPE, ",
        "HREF, WEB_CHK_RULE, SERVER_CHK_RULE, OPTION_TYPE, OPTION_VALUE, CRUD_DS_ID, ",
        "DELETED, CREATED_AT, UPDATED_AT",
        "from CRUD_ITEM",
        "where ID = #{id,jdbcType=DECIMAL}"
    })
    @ResultMap("BaseResultMap")
    CrudItem selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") CrudItem record, @Param("example") CrudItemExample example);

    int updateByExample(@Param("record") CrudItem record, @Param("example") CrudItemExample example);

    int updateByPrimaryKeySelective(CrudItem record);

    @Update({
        "update CRUD_ITEM",
        "set CRUD_DEF_ID = #{crudDefId,jdbcType=DECIMAL},",
          "FK_TYPE = #{fkType,jdbcType=VARCHAR},",
          "TITLE = #{title,jdbcType=VARCHAR},",
          "VAR_NAME = #{varName,jdbcType=VARCHAR},",
          "ITEM_TYPE = #{itemType,jdbcType=VARCHAR},",
          "INPUT_TYPE = #{inputType,jdbcType=VARCHAR},",
          "ACTION_TYPE = #{actionType,jdbcType=VARCHAR},",
          "HREF = #{href,jdbcType=VARCHAR},",
          "WEB_CHK_RULE = #{webChkRule,jdbcType=VARCHAR},",
          "SERVER_CHK_RULE = #{serverChkRule,jdbcType=VARCHAR},",
          "OPTION_TYPE = #{optionType,jdbcType=VARCHAR},",
          "OPTION_VALUE = #{optionValue,jdbcType=VARCHAR},",
          "CRUD_DS_ID = #{crudDsId,jdbcType=DECIMAL},",
          "DELETED = #{deleted,jdbcType=DECIMAL},",
          "CREATED_AT = #{createdAt,jdbcType=TIMESTAMP},",
          "UPDATED_AT = #{updatedAt,jdbcType=TIMESTAMP}",
        "where ID = #{id,jdbcType=DECIMAL}"
    })
    int updateByPrimaryKey(CrudItem record);
}