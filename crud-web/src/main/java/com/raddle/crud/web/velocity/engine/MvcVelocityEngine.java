package com.raddle.crud.web.velocity.engine;

import java.io.StringWriter;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.velocity.VelocityContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.view.velocity.VelocityConfig;

/**
 * 类MvcVelocityEngine.java的实现描述：用mvc的velocity引擎渲染
 * @author raddle60 2013-3-10 下午7:02:55
 */
public class MvcVelocityEngine {

    @Autowired
    private VelocityConfig velocityConfig;

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
}
