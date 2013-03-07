package com.raddle.crud.extdao.dbinfo.model;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class TableInfo {

    private String                  tableName;
    private String                  comment;
    private Map<String, ColumnInfo> columnMap = new HashMap<String, ColumnInfo>();

    public void addColumnInfo(String columnName, ColumnInfo columnInfo) {
        this.columnMap.put(columnName, columnInfo);
    }

    public ColumnInfo getColumnInfo(String columnName) {
        return ((ColumnInfo) this.columnMap.get(columnName));
    }

    public Collection<ColumnInfo> getColumnInfos() {
        return this.columnMap.values();
    }

    public String getTableName() {
        return this.tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getComment() {
        return this.comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
