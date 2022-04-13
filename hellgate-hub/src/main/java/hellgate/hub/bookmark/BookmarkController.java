package hellgate.hub.bookmark;

import hellgate.common.account.Account;
import hellgate.common.account.CurrentUser;
import hellgate.common.account.IdCardForm;
import hellgate.common.script.Script;
import hellgate.hub.account.HubAccountService;
import hellgate.hub.script.ScriptService;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/bookmark")
public class BookmarkController {

    private final HubAccountService hubAccountService;
    private final ScriptService scriptService;

    public BookmarkController(HubAccountService hubAccountService, ScriptService scriptService) {
        this.hubAccountService = hubAccountService;
        this.scriptService = scriptService;
    }

    @GetMapping(params = {HubAccountService.USERNAME_KEY, HubAccountService.PASSWORD_KEY, "timestamp"})
    public String index(@CurrentUser UserDetails userDetails,
                        @Param(HubAccountService.PASSWORD_KEY) String password,
                        @ModelAttribute IdCardForm form, Model model) {
        Account account = hubAccountService.findByUserDetails(userDetails);
        model.addAttribute(HubAccountService.USERNAME_KEY, account.getUsername());
        model.addAttribute(HubAccountService.UID_KEY, account.getUid());
        model.addAttribute(HubAccountService.PASSWORD_KEY, password);
        if (account.getCard() == null) {
            return "account/id-card";
        }

        //noinspection DuplicatedCode
        Script lastScript = Optional.ofNullable(account.getLastScript())
                .orElseGet(scriptService::getNewest);
        model.addAttribute("lastScript", lastScript);
        List<Script> scripts = scriptService.listByRecommend().stream()
                .filter(it -> !it.equals(lastScript))
                .collect(Collectors.toList());
        model.addAttribute("scripts", scripts);
        return "account/bookmark";
    }
}
