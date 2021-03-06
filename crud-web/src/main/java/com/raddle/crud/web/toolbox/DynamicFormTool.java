package com.raddle.crud.web.toolbox;

import java.io.StringWriter;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.tools.generic.EscapeTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.support.RequestContext;
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
    @SuppressWarnings({ "rawtypes", "unchecked" })
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
        Map<String, Object> subResults = (Map<String, Object>) request.getAttribute("subResults");
        if (subResults != null) {
            for (Map.Entry<String, Object> entry : subResults.entrySet()) {
                velocityContext.put(entry.getKey(), entry.getValue());
            }
        }
        velocityContext.put("formTool", this);
        velocityContext.put("request", request);
        velocityContext.put("springMacroRequestContext", new RequestContext(request));
        velocityContext.put("stringUtils", new StringUtils());
        velocityContext.put("stringEscapeUtils", new StringEscapeUtils());
        velocityContext.put("dateUtils", new DateUtils());
        velocityContext.put("dateFormatUtils", new DateFormatUtils());
        velocityContext.put("dateTool", new DateTool());
        velocityContext.put("escape", new EscapeTool());
        StringWriter writer = new StringWriter();
        velocityConfig.getVelocityEngine().evaluate(velocityContext, writer, "randerExpr", expr);
        return writer.toString();
    }

    /**
     * 渲染href表达式,trim所有空白符
     * @param expr vm表达式
     * @param context map或pojo
     * @param request
     * @return null或非空白
     */
    public String randerHref(String expr, Object context, HttpServletRequest request) {
        String content = randerExpr(expr, context, request);
        if (content != null) {
            content = content.replaceAll("\\s", "");
        }
        if (StringUtils.isEmpty(content)) {
            return null;
        }
        return content;
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
