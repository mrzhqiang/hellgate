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
        if (Authentications.logged()) {
            return "redirect:/";
        }
        Authentications.ofMessage(session)
                .ifPresent(message -> model.addAttribute("message", message));
        return "login";
    }
}
