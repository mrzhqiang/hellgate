package hellgate.api.controller.account;

import hellgate.common.annotation.CurrentUser;
import hellgate.common.model.account.Account;
import hellgate.common.model.account.IdentityCard;
import hellgate.common.model.account.IdentityCardForm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/identity-card")
public class IdentityCardController {

    private final IdentityCardService service;
    private final AccountService accountService;

    public IdentityCardController(IdentityCardService service, AccountService accountService) {
        this.service = service;
        this.accountService = accountService;
    }

    @GetMapping
    public String index(@CurrentUser UserDetails userDetails,
                        @ModelAttribute IdentityCardForm form, Model model) {
        Account account = accountService.findByUserDetails(userDetails);
        model.addAttribute("username", account.getUsername());
        model.addAttribute("uid", account.getUid());
        return "account/identity-card";
    }

    @PostMapping
    public String binding(@CurrentUser UserDetails userDetails,
                          @Validated @ModelAttribute IdentityCardForm form,
                          BindingResult result, RedirectAttributes attributes) {
        if (result.hasErrors()) {
            return "account/identity-card";
        }

        IdentityCard card = service.convert(form);
        if (card == null) {
            result.reject("CardController.failed");
            return "account/identity-card";
        }

        accountService.binding(userDetails, card);
        attributes.addFlashAttribute("message", "CardController.success");
        return "redirect:/stage";
    }
}
