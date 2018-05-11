package com.guochaojava.controller;

import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author guochao.
 * @since
 */
@Controller
public class IndexController {

    @RequiresRoles("admin")
    @RequestMapping(value = "/index")
    public String index() {

        return "index";

    }
}