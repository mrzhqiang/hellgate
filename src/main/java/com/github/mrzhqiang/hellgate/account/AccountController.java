package com.github.mrzhqiang.hellgate.account;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Optional;

@Controller
public class AccountController {
    private static final String USERNAME_ALREADY_EXISTS_FORMAT = "注册失败，账号 [%s] 已存在！";
    private static final String CREATE_SUCCESSFUL_FORMAT = "账号 [%s] 创建成功！";

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/login")
    public String login(Model model, HttpSession session) {
        Authentications.checkMessage(model, session);
        model.addAttribute(AccountForm.NAME, new AccountForm());
        return "login";
    }

    @GetMapping("/register")
    public String register(Model model, HttpSession session) {
        Authentications.checkMessage(model, session);
        model.addAttribute(AccountForm.NAME, new AccountForm());
        return "register";
    }

    @GetMapping("/register-help")
    public String registerHelp() {
        return "register-help";
    }

    @PostMapping("/register")
    public String register(@Valid AccountForm accountForm, BindingResult result, RedirectAttributes attributes) {
        if (result.hasErrors()) {
            result.getModel().put(AccountForm.NAME, accountForm);
            return "register";
        }
        String username = accountForm.getUsername();
        Optional<Account> optionalAccount = accountService.find(username);
        if (optionalAccount.isPresent()) {
            Account account = optionalAccount.get();
            String message = String.format(USERNAME_ALREADY_EXISTS_FORMAT, account.getUsername());
            result.addError(new FieldError(AccountForm.NAME, AccountForm.FIELD_USERNAME, message));
            result.getModel().put(AccountForm.NAME, accountForm);
            return "register";
        }
        accountService.create(username, accountForm.getPassword());
        String message = String.format(CREATE_SUCCESSFUL_FORMAT, username);
        attributes.addFlashAttribute("alert", message);
        return "redirect:/login";
    }
}
