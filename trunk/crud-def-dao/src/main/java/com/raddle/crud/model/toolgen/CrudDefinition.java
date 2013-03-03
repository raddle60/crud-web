package com.raddle.crud.model.toolgen;

import java.util.Date;

public class CrudDefinition {
    private Long id;

    private String code;

    private String name;

    private String defType;

    private String tableName;

    private Long crudDsId;

    private String preSql;

    private String sqlType;

    private String sql;

    private String createTmCol;

    private String updateTmCol;

    private Short deleted;

    private Date createdAt;

    private Date updatedAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getDefType() {
        return defType;
    }

    public void setDefType(String defType) {
        this.defType = defType == null ? null : defType.trim();
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName == null ? null : tableName.trim();
    }

    public Long getCrudDsId() {
        return crudDsId;
    }

    public void setCrudDsId(Long crudDsId) {
        this.crudDsId = crudDsId;
    }

    public String getPreSql() {
        return preSql;
    }

    public void setPreSql(String preSql) {
        this.preSql = preSql == null ? null : preSql.trim();
    }

    public String getSqlType() {
        return sqlType;
    }

    public void setSqlType(String sqlType) {
        this.sqlType = sqlType == null ? null : sqlType.trim();
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql == null ? null : sql.trim();
    }

    public String getCreateTmCol() {
        return createTmCol;
    }

    public void setCreateTmCol(String createTmCol) {
        this.createTmCol = createTmCol == null ? null : createTmCol.trim();
    }

    public String getUpdateTmCol() {
        return updateTmCol;
    }

    public void setUpdateTmCol(String updateTmCol) {
        this.updateTmCol = updateTmCol == null ? null : updateTmCol.trim();
    }

    public Short getDeleted() {
        return deleted;
    }

    public void setDeleted(Short deleted) {
        this.deleted = deleted;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
}