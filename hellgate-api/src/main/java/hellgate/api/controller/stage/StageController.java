package hellgate.api.controller.stage;

import hellgate.api.controller.account.AccountService;
import hellgate.common.annotation.CurrentUser;
import hellgate.common.model.account.Account;
import hellgate.common.model.stage.Stage;
import org.springframework.security.core.userdetails.UserDetails;
import static org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_USERNAME_KEY;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/stage")
public class StageController {

    private final AccountService accountService;
    private final StageService service;

    public StageController(AccountService accountService,
                           StageService stageService) {
        this.service = stageService;
        this.accountService = accountService;
    }

    @GetMapping
    public String index(@CurrentUser UserDetails userDetails, Model model) {
        Account account = accountService.findByUserDetails(userDetails);
        model.addAttribute(SPRING_SECURITY_FORM_USERNAME_KEY, account.getUsername());
        model.addAttribute("uid", account.getUid());
        if (account.getCard() == null) {
            return "account/identity-card";
        }

        List<Stage> stages = service.listByRecommend();
        model.addAttribute("stages", stages);
        model.addAttribute("lastStage", account.getLastScript());
        return "stage/index";
    }
}
