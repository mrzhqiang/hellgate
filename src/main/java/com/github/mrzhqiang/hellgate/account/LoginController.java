package com.github.mrzhqiang.hellgate.account;

import com.github.mrzhqiang.hellgate.common.Sessions;
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
    public String index(HttpSession session, Model model, @ModelAttribute AccountForm form) {
        Sessions.httpException(session, model);
        return "login";
    }
}
