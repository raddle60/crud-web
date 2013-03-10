package com.raddle.crud.web.toolbox;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;

import com.raddle.crud.enums.ItemType;
import com.raddle.crud.model.toolgen.CrudItem;

/**
 * 类DynamicFormTool.java的实现描述：类实现描述 为渲染页面提供一些方法
 * @author raddle60 2013-3-9 下午7:41:41
 */
public class DynamicFormTool {

    public String getItemVmFileName(CrudItem item) {
        StringBuilder sb = new StringBuilder();
        sb.append(item.getItemType().toLowerCase());
        if (item.getItemType().equals(ItemType.INPUT.name()) && StringUtils.isNotEmpty(item.getInputType())) {
            sb.append("-" + item.getInputType().toLowerCase());
        }
        if (item.getItemType().equals(ItemType.ACTION.name()) && StringUtils.isNotEmpty(item.getActionType())) {
            sb.append("-" + item.getActionType().toLowerCase());
        }
        sb.append(".vm");
        return sb.toString();
    }

    /**
     * 获得变量的值
     * @return
     * @throws Exception
     */
    public Object v(Object bean, String varName, HttpServletRequest request) throws Exception {
        if (bean == null || StringUtils.isEmpty(varName)) {
            return null;
        }
        Object v = null;
        if (bean != null) {
            v = PropertyUtils.getProperty(bean, varName);
        }
        if (v == null) {
            v = request.getParameter(varName);
        }
        return v;
    }
}
