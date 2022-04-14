package hellgate.hub.bookmark;

import hellgate.common.account.Account;
import hellgate.common.account.CurrentUser;
import hellgate.common.account.IdCardForm;
import hellgate.hub.account.HubAccountService;
import hellgate.hub.script.ScriptData;
import hellgate.hub.script.ScriptService;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/bookmark")
public class BookmarkController {

    private final HubAccountService accountService;
    private final ScriptService scriptService;

    public BookmarkController(HubAccountService accountService, ScriptService scriptService) {
        this.accountService = accountService;
        this.scriptService = scriptService;
    }

    @GetMapping(params = {HubAccountService.USERNAME_KEY, HubAccountService.PASSWORD_KEY, "timestamp"})
    public String index(@CurrentUser UserDetails userDetails,
                        @Param(HubAccountService.PASSWORD_KEY) String password,
                        @ModelAttribute IdCardForm form, Model model) {
        Account account = accountService.findByUserDetails(userDetails);
        model.addAttribute(HubAccountService.USERNAME_KEY, account.getUsername());
        model.addAttribute(HubAccountService.UID_KEY, account.getUid());
        model.addAttribute(HubAccountService.PASSWORD_KEY, password);
        if (account.getCard() == null) {
            return "account/id-card";
        }

        ScriptData data = scriptService.loadBy(account);
        model.addAttribute("data", data);
        return "script/index";
    }
}
