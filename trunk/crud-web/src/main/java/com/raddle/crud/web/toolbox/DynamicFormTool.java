package com.raddle.crud.web.toolbox;

import java.io.StringWriter;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.velocity.VelocityContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.view.velocity.VelocityConfig;

import com.raddle.crud.biz.DynamicFormManager;
import com.raddle.crud.enums.ItemType;
import com.raddle.crud.model.toolgen.CrudItem;

/**
 * 类DynamicFormTool.java的实现描述：类实现描述 为渲染页面提供一些方法
 * @author raddle60 2013-3-9 下午7:41:41
 */
public class DynamicFormTool {

    @Autowired
    private VelocityConfig velocityConfig;

    @Autowired
    private DynamicFormManager dynamicFormManager;

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
    public Object v(Object bean, String varName) throws Exception {
        if (bean == null || StringUtils.isEmpty(varName)) {
            return null;
        }
        Object v = null;
        if (bean != null) {
            v = PropertyUtils.getProperty(bean, varName);
        }
        return v;
    }

    /**
     * 获得变量的枚举名称，如果不是枚举，返回值
     * @return
     * @throws Exception
     */
    public Object lOfv(CrudItem item, Object bean, String varName) throws Exception {
        if (item == null || bean == null || StringUtils.isEmpty(varName)) {
            return null;
        }
        Object v = v(bean, varName);
        if (v != null) {
            if (StringUtils.isBlank(item.getOptionType())) {
                return v;
            } else {
                List<Map<String, Object>> ops = ops(item);
                for (Map<String, Object> map : ops) {
                    if (v.toString().equals(map.get("key").toString())) {
                        return map.get("value") + "[" + v + "]";
                    }
                }
            }
        }
        return v;
    }

    /**
     * 获得行数
     * @param inputSize
     * @return
     */
    public String rows(String inputSize) {
        if (StringUtils.isBlank(inputSize)) {
            return "10";
        } else {
            return StringUtils.split(inputSize, ",")[0];
        }
    }

    /**
     * 获得列数
     * @param inputSize
     * @return
     */
    public String cols(String inputSize) {
        if (StringUtils.isBlank(inputSize)) {
            return "60";
        } else {
            String[] split = StringUtils.split(inputSize, ",");
            if (split.length > 1) {
                return split[1];
            } else {
                return "60";
            }
        }
    }

    /**
     * 渲染表达式
     * @param expr vm表达式
     * @param context map或pojo
     * @param request
     * @return
     */
    @SuppressWarnings("rawtypes")
    public String randerExpr(String expr, Object context, HttpServletRequest request) {
        if (StringUtils.isBlank(expr)) {
            return null;
        }
        VelocityContext velocityContext = null;
        if (context != null) {
            if (context instanceof Map) {
                velocityContext = new VelocityContext((Map) context);
            } else {
                try {
                    velocityContext = new VelocityContext(PropertyUtils.describe(context));
                } catch (Exception e) {
                    throw new RuntimeException(e.getMessage(), e);
                }
            }
        }
        if (velocityContext == null) {
            velocityContext = new VelocityContext();
        }
        velocityContext.put("request", request);
        StringWriter writer = new StringWriter();
        velocityConfig.getVelocityEngine().evaluate(velocityContext, writer, "randerExpr", expr);
        return writer.toString();
    }

    public List<Map<String, Object>> ops(CrudItem crudItem) {
        return dynamicFormManager.getItemOptions(crudItem);
    }

    public boolean eq(Object a, Object b) {
        return ObjectUtils.equals(a != null ? String.valueOf(a) : "", b != null ? String.valueOf(b) : "");
    }

    public boolean in(Object a, Object b) {
        String as = a != null ? String.valueOf(a) : "";
        String bs = b != null ? String.valueOf(b) : "";
        if (eq(as, bs)) {
            return true;
        }
        if (as.length() > bs.length()) {
            return include(as, bs);
        } else {
            return include(bs, as);
        }
    }

    private boolean include(String container, String v) {
        return ("," + container + ",").indexOf("," + v + ",") != -1;
    }
}
