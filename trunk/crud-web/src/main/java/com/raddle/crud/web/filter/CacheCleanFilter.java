package com.raddle.crud.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import com.raddle.crud.biz.impl.VelocityFormManager;
import com.raddle.crud.extdao.impl.JdbcDynamicFormDao;

/**
 * 类ThreadCacheClearFilter.java的实现描述：清除线程缓存
 * @author raddle60 2013-7-14 下午3:37:26
 */
public class CacheCleanFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        try {
            chain.doFilter(request, response);
        } finally {
            JdbcDynamicFormDao.clearThreadCache();
            VelocityFormManager.clearThreadCache();
        }
    }

    @Override
    public void destroy() {

    }

}
