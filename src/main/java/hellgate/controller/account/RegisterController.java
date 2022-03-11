package hellgate.controller.account;

import hellgate.common.Messages;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/register")
public class RegisterController {

    private final AccountService accountService;

    public RegisterController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping
    public String index(@ModelAttribute AccountForm form) {
        return "account/register";
    }

    @PostMapping
    public String register(@Valid @ModelAttribute AccountForm form,
                           BindingResult result, RedirectAttributes attributes) {
        if (result.hasErrors()) {
            // 提示字段 validate 出错，包括验证码处理
            return "account/register";
        }

        if (accountService.register(form)) {
            Messages.success(attributes, Messages.REGISTER_SUCCESSFUL);
            return "redirect:/login";
        }

        result.reject(Messages.REGISTER_FAILED);
        return "account/register";
    }

    @GetMapping("/help")
    public String help() {
        return "account/register-help";
    }
}
