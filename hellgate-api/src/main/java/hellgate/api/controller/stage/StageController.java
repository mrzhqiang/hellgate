package hellgate.api.controller.stage;

import hellgate.api.controller.account.AccountService;
import hellgate.common.account.Account;
import hellgate.common.account.CurrentUser;
import hellgate.common.stage.Stage;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/stage")
public class StageController {

    private final AccountService accountService;
    private final StageService stageService;

    public StageController(AccountService accountService,
                           StageService stageService) {
        this.stageService = stageService;
        this.accountService = accountService;
    }

    @GetMapping
    public String index(@CurrentUser UserDetails userDetails, Model model) {
        Account account = accountService.findByUserDetails(userDetails);
        model.addAttribute(AccountService.USERNAME_KEY, account.getUsername());
        model.addAttribute(AccountService.UID_KEY, account.getUid());

        //noinspection DuplicatedCode
        Stage lastStage = Optional.ofNullable(account.getLastStage())
                .orElseGet(stageService::getNewest);
        model.addAttribute("lastStage", lastStage);
        List<Stage> stages = stageService.listByRecommend().stream()
                .filter(it -> !it.equals(lastStage))
                .collect(Collectors.toList());
        model.addAttribute("stages", stages);
        return "stage/index";
    }
}
