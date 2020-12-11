package com.github.mrzhqiang.hellgate.account;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/login")
public class LoginController {

    @GetMapping
    public String index(Model model, HttpSession session) {
        Authentications.checkMessage(model, session);
        if (!model.containsAttribute(AccountForm.NAME)) {
            model.addAttribute(AccountForm.NAME, new AccountForm());
        }
        return "login";
    }
}
