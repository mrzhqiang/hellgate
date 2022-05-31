package hellgate.hub.account;

import hellgate.common.account.Account;
import hellgate.common.account.CurrentUser;
import hellgate.common.idcard.IdCardForm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/id-card")
public class IdCardController {

    private final HubAccountService hubAccountService;

    public IdCardController(HubAccountService hubAccountService) {
        this.hubAccountService = hubAccountService;
    }

    @PostMapping
    public String bind(@Validated @ModelAttribute IdCardForm form,
                       BindingResult result, Model model,
                       @CurrentUser UserDetails userDetails) {
        Account account = hubAccountService.findByUserDetails(userDetails);
        model.addAttribute(HubAccountService.USERNAME_KEY, account.getUsername());
        model.addAttribute(HubAccountService.UID_KEY, account.getUid());
        if (result.hasErrors()) {
            return "account/id-card";
        }

        boolean binding = hubAccountService.binding(userDetails, form);
        if (!binding) {
            result.reject("IdCardController.failed",
                    new Object[]{HubAccountService.BIND_ID_CARD_MAX}, null);
            return "account/id-card";
        }

        return "redirect:/script";
    }
}
