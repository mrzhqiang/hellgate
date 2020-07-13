package com.github.mrzhqiang.hellgateapi.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * 主页控制器。
 * <p>
 * 与页面相关的跳转，在这里进行。
 */
@Controller
public class HomeController {

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/admin")
    public String admin(Model model, HttpServletRequest request) {
        return "admin";
    }
}
