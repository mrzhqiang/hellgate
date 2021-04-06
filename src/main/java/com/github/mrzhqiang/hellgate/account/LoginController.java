package com.github.mrzhqiang.hellgate.account;

import com.github.mrzhqiang.hellgate.util.HttpSessions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/login")
public class LoginController {

    @GetMapping
    public String index(@ModelAttribute AccountForm form,  Model model, HttpSession session) {
        HttpSessions.handleException(session, model);
        return "login";
    }

}
