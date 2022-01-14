package com.github.mrzhqiang.hellgate.home;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 主页控制器。
 */
@Controller
public class HomeController {

    @GetMapping("/")
    public String index() {
        return "home";
    }
}
