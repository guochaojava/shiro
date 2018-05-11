package com.guochaojava.controller;

import com.guochaojava.model.User;
import com.guochaojava.util.Result;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author GuoChao.
 * @since
 */
@Controller
public class LoginController {

    @RequestMapping("/toLogin")
    public String check() {
        return "login";
    }

    @RequestMapping(value = "/login")
    @ResponseBody
    public Object login(User user, HttpServletRequest request, HttpServletResponse response) {
        try {
            Subject subject = SecurityUtils.getSubject();
            UsernamePasswordToken token = new UsernamePasswordToken(user.getEmail(), user.getPassword());
            token.setRememberMe(user.isRememberMe());
            subject.login(token);
            return Result.buildOK("登录成功").setUrl("./index");
        } catch (Exception e) {
            e.printStackTrace();
            return Result.buildError("用户名或密码错误");
        }
    }

    @RequestMapping(value = "/logout")
    public String logout(User user, HttpServletRequest request, HttpServletResponse response) {

            Subject subject = SecurityUtils.getSubject();
            subject.logout();
            return "redirect:/index";

    }


    @RequiresRoles("admin")
    @RequestMapping(value = "/test")
    @ResponseBody
    public Object test(HttpServletRequest request, HttpServletResponse response) {
        try {

            return Result.buildOK("test测试成功");
        } catch (Exception e) {
            e.printStackTrace();
            return Result.buildError("test测试失败");
        }
    }

    @RequiresPermissions("、test")
    @RequestMapping(value = "/test1")
    @ResponseBody
    public Object test1(HttpServletRequest request, HttpServletResponse response) {
        try {

            return Result.buildOK("test1测试成功");
        } catch (Exception e) {
            e.printStackTrace();
            return Result.buildError("test1测试失败");
        }
    }
}