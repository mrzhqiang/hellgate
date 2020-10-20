package com.github.mrzhqiang.hellgate.account;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

@Controller
public class AccountController {

    @GetMapping("/login")
    public String login(Model model, HttpSession session) {
        if (Authentications.logged()) {
            return "redirect:/";
        }
        Authentications.ofMessage(session)
                .ifPresent(message -> model.addAttribute("message", message));
        return "login";
    }
}
