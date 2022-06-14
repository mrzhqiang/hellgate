package hellgate.hub.account;

import com.google.common.base.Strings;
import hellgate.common.account.AccountForm;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/register")
public class RegisterController {

    private final HubAccountService hubAccountService;

    public RegisterController(HubAccountService hubAccountService) {
        this.hubAccountService = hubAccountService;
    }

    @GetMapping
    public String index(@ModelAttribute AccountForm form) {
        return "account/register";
    }

    @PostMapping
    public String register(@Validated @ModelAttribute AccountForm form, BindingResult result) {
        if (result.hasErrors()) {
            return "account/register";
        }

        String successUrl = hubAccountService.register(form);
        if (Strings.isNullOrEmpty(successUrl)) {
            result.reject("RegisterController.failed");
            return "account/register";
        }

        return "redirect:" + successUrl;
    }

    @GetMapping("/help")
    public String help() {
        return "account/register-help";
    }
}
