package com.raddle.crud.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class DatasourceController extends BaseController {

    @RequestMapping(value = "def/datasource/list")
    public String list(ModelMap model, HttpServletResponse response, HttpServletRequest request) {

        return "def/datasource/list";
    }
}
