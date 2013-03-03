package com.raddle.mybatis.generator.plugin;

import java.util.List;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;

public class ForceJdbcTypeTimestampPlugin extends PluginAdapter {

    @Override
    public boolean validate(List<String> warnings) {
        return true;
    }

    @Override
    public void initialized(IntrospectedTable introspectedTable) {
        for (IntrospectedColumn introspectedColumn : introspectedTable.getAllColumns()) {
            if (introspectedColumn.getJdbcTypeName().equals("DATE") || introspectedColumn.getJdbcTypeName().equals("TIME")) {
                introspectedColumn.setJdbcTypeName("TIMESTAMP");
            }
        }
    }

}
