package com.raddle.mybatis.generator.plugin;

import java.util.List;
import java.util.Properties;

import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.Document;
import org.mybatis.generator.api.dom.xml.XmlElement;

public class SqlMapNameSpaceRenamePlugin extends PluginAdapter {

    private String namespace;

    @Override
    public boolean validate(List<String> warnings) {
        return true;
    }

    @Override
    public void setProperties(Properties properties) {
        namespace = properties.getProperty("namespace");
    }

    @Override
    public boolean sqlMapDocumentGenerated(Document document, IntrospectedTable introspectedTable) {
        XmlElement rootElement = document.getRootElement();
        List<Attribute> attributes = rootElement.getAttributes();
        for (int i = 0; i < attributes.size(); i++) {
            Attribute attribute = attributes.get(i);
            if (attribute.getName().equals("namespace")) {
                String baseRecordType = introspectedTable.getBaseRecordType();
                String simpleClassName = baseRecordType.substring(baseRecordType.lastIndexOf(".") + 1);
                attributes.set(i, new Attribute(attribute.getName(), namespace.replace("${simpleClassName}", simpleClassName)));
                break;
            }
        }
        return true;
    }

    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

}
