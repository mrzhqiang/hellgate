package hellgate.api.controller.account;

import hellgate.common.model.account.AccountForm;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    public String register(@Validated @ModelAttribute AccountForm form,
                           BindingResult result, RedirectAttributes attributes) {
        if (result.hasErrors()) {
            return "account/register";
        }

        if (!accountService.register(form)) {
            result.reject("RegisterController.failed");
            return "account/register";
        }

        attributes.addFlashAttribute("message", "RegisterController.success");
        return "redirect:/login";
    }

    @GetMapping("/help")
    public String help() {
        return "account/register-help";
    }
}
