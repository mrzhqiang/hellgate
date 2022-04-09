package hellgate.api.controller.account;

import hellgate.api.controller.stage.StageService;
import hellgate.common.annotation.CurrentUser;
import hellgate.common.model.account.Account;
import hellgate.common.model.account.IdentityCardForm;
import hellgate.common.model.stage.Stage;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.userdetails.UserDetails;
import static org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_PASSWORD_KEY;
import static org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_USERNAME_KEY;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/bookmark")
public class BookmarkController {

    private final AccountService accountService;
    private final StageService stageService;

    public BookmarkController(AccountService accountService,
                              StageService stageService) {
        this.accountService = accountService;
        this.stageService = stageService;
    }

    @GetMapping(params = {
            SPRING_SECURITY_FORM_USERNAME_KEY,
            SPRING_SECURITY_FORM_PASSWORD_KEY,
            "timestamp"
    })
    public String index(@CurrentUser UserDetails userDetails,
                        @Param(SPRING_SECURITY_FORM_PASSWORD_KEY) String password,
                        @ModelAttribute IdentityCardForm form, Model model) {
        Account account = accountService.findByUserDetails(userDetails);
        model.addAttribute(SPRING_SECURITY_FORM_USERNAME_KEY, account.getUsername());
        model.addAttribute("uid", account.getUid());
        model.addAttribute(SPRING_SECURITY_FORM_PASSWORD_KEY, password);
        if (account.getCard() == null) {
            return "account/identity-card";
        }

        List<Stage> stages = stageService.listByRecommend();
        model.addAttribute("stages", stages);
        model.addAttribute("lastStage", account.getLastScript());
        return "/stage/index";
    }
}
