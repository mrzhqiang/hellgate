package hellgate.controller.account;

import hellgate.common.session.Sessions;
import hellgate.common.logging.Action;
import hellgate.common.logging.ActionType;
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
public class RegisterController {

    private final AccountService accountService;

    public RegisterController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping
    public String index(HttpSession session, Model model, @ModelAttribute AccountForm form) {
        Sessions.httpException(session, model);
        return "account/register";
    }

    @Action(value = "注册账户", type = ActionType.REGISTER)
    @PostMapping
    public String register(@Valid @ModelAttribute AccountForm form,
                           BindingResult result, RedirectAttributes attributes) {
        if (result.hasErrors()) {
            return "account/register";
        }

        if (!accountService.register(form)) {
            result.reject("register.failed");
            return "account/register";
        }

        attributes.addFlashAttribute("alert", "register.successful");
        return "redirect:/login";
    }

    @GetMapping("/help")
    public String help() {
        return "account/register-help";
    }
}
