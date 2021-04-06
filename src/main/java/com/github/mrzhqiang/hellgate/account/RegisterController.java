package com.github.mrzhqiang.hellgate.account;

import com.github.mrzhqiang.hellgate.util.HttpSessions;
import lombok.RequiredArgsConstructor;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
@RequestMapping("/register")
@RequiredArgsConstructor
public class RegisterController {

    private final AccountService accountService;
    private final MessageSourceAccessor accessor;

    @GetMapping
    public String index(@ModelAttribute AccountForm form, Model model, HttpSession session) {
        HttpSessions.handleException(session, model);
        return "register";
    }

    @PostMapping
    public String index(@Valid @ModelAttribute AccountForm form, BindingResult result, RedirectAttributes attributes) {
        if (result.hasErrors()) {
            return "register";
        }

        if (accountService.find(form.getUsername()).isPresent()) {
            result.reject("register.failed");
            return "register";
        }

        accountService.register(form);
        attributes.addFlashAttribute("alert", accessor.getMessage("register.successful"));
        return "redirect:/login";
    }

    @GetMapping("/help")
    public String help() {
        return "register-help";
    }
}
