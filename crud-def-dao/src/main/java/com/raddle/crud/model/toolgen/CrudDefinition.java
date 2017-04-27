package com.raddle.crud.model.toolgen;

import java.util.Date;

public class CrudDefinition {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column CRUD_DEFINITION.ID
     *
     * @mbggenerated
     */
    private Long id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column CRUD_DEFINITION.NAME
     *
     * @mbggenerated
     */
    private String name;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column CRUD_DEFINITION.DEF_TYPE
     *
     * @mbggenerated
     */
    private String defType;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column CRUD_DEFINITION.TABLE_NAME
     *
     * @mbggenerated
     */
    private String tableName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column CRUD_DEFINITION.CRUD_DS_ID
     *
     * @mbggenerated
     */
    private Long crudDsId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column CRUD_DEFINITION.PRE_SQL
     *
     * @mbggenerated
     */
    private String preSql;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column CRUD_DEFINITION.READ_SQL
     *
     * @mbggenerated
     */
    private String readSql;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column CRUD_DEFINITION.KEY_SELECT_SQL
     *
     * @mbggenerated
     */
    private String keySelectSql;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column CRUD_DEFINITION.UPDATE_SQL
     *
     * @mbggenerated
     */
    private String updateSql;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column CRUD_DEFINITION.ADD_HREF
     *
     * @mbggenerated
     */
    private String addHref;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column CRUD_DEFINITION.IS_PAGING
     *
     * @mbggenerated
     */
    private Short isPaging;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column CRUD_DEFINITION.AUTO_MATCH_BTN
     *
     * @mbggenerated
     */
    private Short autoMatchBtn;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column CRUD_DEFINITION.CREATE_TM_COL
     *
     * @mbggenerated
     */
    private String createTmCol;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column CRUD_DEFINITION.UPDATE_TM_COL
     *
     * @mbggenerated
     */
    private String updateTmCol;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column CRUD_DEFINITION.DELETED
     *
     * @mbggenerated
     */
    private Short deleted;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column CRUD_DEFINITION.CREATED_AT
     *
     * @mbggenerated
     */
    private Date createdAt;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column CRUD_DEFINITION.UPDATED_AT
     *
     * @mbggenerated
     */
    private Date updatedAt;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column CRUD_DEFINITION.COMPOSITE_TEMPLATE
     *
     * @mbggenerated
     */
    private String compositeTemplate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column CRUD_DEFINITION.TABLE_SCHEMA
     *
     * @mbggenerated
     */
    private String tableSchema;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column CRUD_DEFINITION.ID
     *
     * @return the value of CRUD_DEFINITION.ID
     *
     * @mbggenerated
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column CRUD_DEFINITION.ID
     *
     * @param id the value for CRUD_DEFINITION.ID
     *
     * @mbggenerated
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column CRUD_DEFINITION.NAME
     *
     * @return the value of CRUD_DEFINITION.NAME
     *
     * @mbggenerated
     */
    public String getName() {
        return name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column CRUD_DEFINITION.NAME
     *
     * @param name the value for CRUD_DEFINITION.NAME
     *
     * @mbggenerated
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column CRUD_DEFINITION.DEF_TYPE
     *
     * @return the value of CRUD_DEFINITION.DEF_TYPE
     *
     * @mbggenerated
     */
    public String getDefType() {
        return defType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column CRUD_DEFINITION.DEF_TYPE
     *
     * @param defType the value for CRUD_DEFINITION.DEF_TYPE
     *
     * @mbggenerated
     */
    public void setDefType(String defType) {
        this.defType = defType == null ? null : defType.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column CRUD_DEFINITION.TABLE_NAME
     *
     * @return the value of CRUD_DEFINITION.TABLE_NAME
     *
     * @mbggenerated
     */
    public String getTableName() {
        return tableName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column CRUD_DEFINITION.TABLE_NAME
     *
     * @param tableName the value for CRUD_DEFINITION.TABLE_NAME
     *
     * @mbggenerated
     */
    public void setTableName(String tableName) {
        this.tableName = tableName == null ? null : tableName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column CRUD_DEFINITION.CRUD_DS_ID
     *
     * @return the value of CRUD_DEFINITION.CRUD_DS_ID
     *
     * @mbggenerated
     */
    public Long getCrudDsId() {
        return crudDsId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column CRUD_DEFINITION.CRUD_DS_ID
     *
     * @param crudDsId the value for CRUD_DEFINITION.CRUD_DS_ID
     *
     * @mbggenerated
     */
    public void setCrudDsId(Long crudDsId) {
        this.crudDsId = crudDsId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column CRUD_DEFINITION.PRE_SQL
     *
     * @return the value of CRUD_DEFINITION.PRE_SQL
     *
     * @mbggenerated
     */
    public String getPreSql() {
        return preSql;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column CRUD_DEFINITION.PRE_SQL
     *
     * @param preSql the value for CRUD_DEFINITION.PRE_SQL
     *
     * @mbggenerated
     */
    public void setPreSql(String preSql) {
        this.preSql = preSql == null ? null : preSql.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column CRUD_DEFINITION.READ_SQL
     *
     * @return the value of CRUD_DEFINITION.READ_SQL
     *
     * @mbggenerated
     */
    public String getReadSql() {
        return readSql;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column CRUD_DEFINITION.READ_SQL
     *
     * @param readSql the value for CRUD_DEFINITION.READ_SQL
     *
     * @mbggenerated
     */
    public void setReadSql(String readSql) {
        this.readSql = readSql == null ? null : readSql.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column CRUD_DEFINITION.KEY_SELECT_SQL
     *
     * @return the value of CRUD_DEFINITION.KEY_SELECT_SQL
     *
     * @mbggenerated
     */
    public String getKeySelectSql() {
        return keySelectSql;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column CRUD_DEFINITION.KEY_SELECT_SQL
     *
     * @param keySelectSql the value for CRUD_DEFINITION.KEY_SELECT_SQL
     *
     * @mbggenerated
     */
    public void setKeySelectSql(String keySelectSql) {
        this.keySelectSql = keySelectSql == null ? null : keySelectSql.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column CRUD_DEFINITION.UPDATE_SQL
     *
     * @return the value of CRUD_DEFINITION.UPDATE_SQL
     *
     * @mbggenerated
     */
    public String getUpdateSql() {
        return updateSql;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column CRUD_DEFINITION.UPDATE_SQL
     *
     * @param updateSql the value for CRUD_DEFINITION.UPDATE_SQL
     *
     * @mbggenerated
     */
    public void setUpdateSql(String updateSql) {
        this.updateSql = updateSql == null ? null : updateSql.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column CRUD_DEFINITION.ADD_HREF
     *
     * @return the value of CRUD_DEFINITION.ADD_HREF
     *
     * @mbggenerated
     */
    public String getAddHref() {
        return addHref;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column CRUD_DEFINITION.ADD_HREF
     *
     * @param addHref the value for CRUD_DEFINITION.ADD_HREF
     *
     * @mbggenerated
     */
    public void setAddHref(String addHref) {
        this.addHref = addHref == null ? null : addHref.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column CRUD_DEFINITION.IS_PAGING
     *
     * @return the value of CRUD_DEFINITION.IS_PAGING
     *
     * @mbggenerated
     */
    public Short getIsPaging() {
        return isPaging;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column CRUD_DEFINITION.IS_PAGING
     *
     * @param isPaging the value for CRUD_DEFINITION.IS_PAGING
     *
     * @mbggenerated
     */
    public void setIsPaging(Short isPaging) {
        this.isPaging = isPaging;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column CRUD_DEFINITION.AUTO_MATCH_BTN
     *
     * @return the value of CRUD_DEFINITION.AUTO_MATCH_BTN
     *
     * @mbggenerated
     */
    public Short getAutoMatchBtn() {
        return autoMatchBtn;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column CRUD_DEFINITION.AUTO_MATCH_BTN
     *
     * @param autoMatchBtn the value for CRUD_DEFINITION.AUTO_MATCH_BTN
     *
     * @mbggenerated
     */
    public void setAutoMatchBtn(Short autoMatchBtn) {
        this.autoMatchBtn = autoMatchBtn;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column CRUD_DEFINITION.CREATE_TM_COL
     *
     * @return the value of CRUD_DEFINITION.CREATE_TM_COL
     *
     * @mbggenerated
     */
    public String getCreateTmCol() {
        return createTmCol;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column CRUD_DEFINITION.CREATE_TM_COL
     *
     * @param createTmCol the value for CRUD_DEFINITION.CREATE_TM_COL
     *
     * @mbggenerated
     */
    public void setCreateTmCol(String createTmCol) {
        this.createTmCol = createTmCol == null ? null : createTmCol.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column CRUD_DEFINITION.UPDATE_TM_COL
     *
     * @return the value of CRUD_DEFINITION.UPDATE_TM_COL
     *
     * @mbggenerated
     */
    public String getUpdateTmCol() {
        return updateTmCol;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column CRUD_DEFINITION.UPDATE_TM_COL
     *
     * @param updateTmCol the value for CRUD_DEFINITION.UPDATE_TM_COL
     *
     * @mbggenerated
     */
    public void setUpdateTmCol(String updateTmCol) {
        this.updateTmCol = updateTmCol == null ? null : updateTmCol.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column CRUD_DEFINITION.DELETED
     *
     * @return the value of CRUD_DEFINITION.DELETED
     *
     * @mbggenerated
     */
    public Short getDeleted() {
        return deleted;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column CRUD_DEFINITION.DELETED
     *
     * @param deleted the value for CRUD_DEFINITION.DELETED
     *
     * @mbggenerated
     */
    public void setDeleted(Short deleted) {
        this.deleted = deleted;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column CRUD_DEFINITION.CREATED_AT
     *
     * @return the value of CRUD_DEFINITION.CREATED_AT
     *
     * @mbggenerated
     */
    public Date getCreatedAt() {
        return createdAt;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column CRUD_DEFINITION.CREATED_AT
     *
     * @param createdAt the value for CRUD_DEFINITION.CREATED_AT
     *
     * @mbggenerated
     */
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column CRUD_DEFINITION.UPDATED_AT
     *
     * @return the value of CRUD_DEFINITION.UPDATED_AT
     *
     * @mbggenerated
     */
    public Date getUpdatedAt() {
        return updatedAt;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column CRUD_DEFINITION.UPDATED_AT
     *
     * @param updatedAt the value for CRUD_DEFINITION.UPDATED_AT
     *
     * @mbggenerated
     */
    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column CRUD_DEFINITION.COMPOSITE_TEMPLATE
     *
     * @return the value of CRUD_DEFINITION.COMPOSITE_TEMPLATE
     *
     * @mbggenerated
     */
    public String getCompositeTemplate() {
        return compositeTemplate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column CRUD_DEFINITION.COMPOSITE_TEMPLATE
     *
     * @param compositeTemplate the value for CRUD_DEFINITION.COMPOSITE_TEMPLATE
     *
     * @mbggenerated
     */
    public void setCompositeTemplate(String compositeTemplate) {
        this.compositeTemplate = compositeTemplate == null ? null : compositeTemplate.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column CRUD_DEFINITION.TABLE_SCHEMA
     *
     * @return the value of CRUD_DEFINITION.TABLE_SCHEMA
     *
     * @mbggenerated
     */
    public String getTableSchema() {
        return tableSchema;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column CRUD_DEFINITION.TABLE_SCHEMA
     *
     * @param tableSchema the value for CRUD_DEFINITION.TABLE_SCHEMA
     *
     * @mbggenerated
     */
    public void setTableSchema(String tableSchema) {
        this.tableSchema = tableSchema == null ? null : tableSchema.trim();
    }
}