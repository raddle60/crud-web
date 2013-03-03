package com.raddle.crud.web.exception.resolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.AbstractHandlerExceptionResolver;

/**
 * description: 仅记录异常日志的Resolver
 * @author xurong.raddle
 * time : 2012-9-15 下午2:18:04
 */
public class LogExceptionOnlyResolver extends AbstractHandlerExceptionResolver {

    private static final Logger logger = LoggerFactory.getLogger(LogExceptionOnlyResolver.class);

    @Override
    protected ModelAndView doResolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        logger.error(ex.getMessage(), ex);
        return null;
    }

    @Override
    public int getOrder() {
        // 记录日志的优先级最高
        return Ordered.HIGHEST_PRECEDENCE;
    }
}
