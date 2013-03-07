package com.raddle.crud.extdao.dbinfo.comment;

public abstract interface TableCommentGetter {

    public String getTableComment(String tableName);

    public String getColumnComment(String tableName, String columnName);
}
