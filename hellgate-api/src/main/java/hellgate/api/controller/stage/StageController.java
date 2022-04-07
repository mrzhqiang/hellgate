package hellgate.api.controller.stage;

import hellgate.api.controller.account.AccountService;
import hellgate.common.annotation.CurrentUser;
import hellgate.common.model.account.Account;
import hellgate.common.model.stage.Stage;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/stage")
public class StageController {

    private final StageService service;
    private final AccountService accountService;

    public StageController(StageService stageService, AccountService accountService) {
        this.service = stageService;
        this.accountService = accountService;
    }

    @GetMapping
    public String index(@CurrentUser UserDetails userDetails, Model model) {
        Account account = accountService.findByUserDetails(userDetails);
        if (account.getCard() == null) {
            return "redirect:/identity-card";
        }

        List<Stage> stages = service.listByRecommend();
        model.addAttribute("stages", stages);
        model.addAttribute("lastStage", account.getLastScript());
        return "/stage/index";
    }
}
