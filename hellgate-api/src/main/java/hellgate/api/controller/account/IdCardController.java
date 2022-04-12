package hellgate.api.controller.account;

import hellgate.common.annotation.CurrentUser;
import hellgate.common.model.account.Account;
import hellgate.common.model.account.IdCardForm;
import hellgate.common.util.Views;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/id-card")
public class IdCardController {

    private final AccountService accountService;

    public IdCardController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping
    public String bind(@CurrentUser UserDetails userDetails,
                       @Validated @ModelAttribute IdCardForm form, Model model,
                       BindingResult result, RedirectAttributes attributes) {
        Account account = accountService.findByUserDetails(userDetails);
        model.addAttribute(AccountService.USERNAME_KEY, account.getUsername());
        model.addAttribute(AccountService.UID_KEY, account.getUid());
        if (result.hasErrors()) {
            return "account/id-card";
        }

        boolean binding = accountService.binding(userDetails, form);
        if (!binding) {
            result.reject("IdCardController.failed");
            return "account/id-card";
        }

        attributes.addFlashAttribute(Views.KEY_MESSAGE, "IdCardController.success");
        return "redirect:/stage";
    }
}
