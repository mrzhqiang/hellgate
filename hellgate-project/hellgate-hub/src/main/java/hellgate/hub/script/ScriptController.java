package hellgate.hub.script;

import hellgate.common.account.Account;
import hellgate.common.account.CurrentUser;
import hellgate.hub.account.HubAccountService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/script")
public class ScriptController {

    private final HubAccountService accountService;
    private final ScriptService scriptService;

    public ScriptController(HubAccountService accountService,
                            ScriptService scriptService) {
        this.accountService = accountService;
        this.scriptService = scriptService;
    }

    @GetMapping
    public String index(@CurrentUser UserDetails userDetails, Model model) {
        Account account = accountService.findByUser(userDetails);
        model.addAttribute(HubAccountService.USERNAME_KEY, account.getUsername());
        model.addAttribute(HubAccountService.UID_KEY, account.getUid());

        ScriptData data = scriptService.loadBy(account);
        model.addAttribute("data", data);
        return "script/index";
    }
}
