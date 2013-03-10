package com.raddle.crud.web.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONSerializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BaseController {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 写入json串
     * @param jsonObject
     * @param response
     * @return 返回null，有返回值，只是方便使用者return
     * @throws IOException
     */
    protected String writeJson(Object jsonObject, HttpServletResponse response) throws IOException {
        response.setContentType("application/json; charset=UTF-8");
        setNoCacheHeaders(response);
        response.getWriter().write(JSONSerializer.toJSON(jsonObject).toString());
        response.getWriter().flush();
        response.getWriter().close();
        return null;
    }

    protected void setNoCacheHeaders(HttpServletResponse response) {
        // Set standard HTTP/1.1 no-cache headers.
        response.setHeader("Cache-Control", "no-store, max-age=0, no-cache, must-revalidate");

        // Set IE extended HTTP/1.1 no-cache headers.
        response.addHeader("Cache-Control", "post-check=0, pre-check=0");

        // Set standard HTTP/1.0 no-cache header.
        response.setHeader("Pragma", "no-cache");
    }
}
