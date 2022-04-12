package hellgate.api.controller.account;

import hellgate.api.controller.stage.StageService;
import hellgate.common.annotation.CurrentUser;
import hellgate.common.model.account.Account;
import hellgate.common.model.account.IdCardForm;
import hellgate.common.model.stage.Stage;
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

    private final AccountService accountService;
    private final StageService stageService;

    public BookmarkController(AccountService accountService, StageService stageService) {
        this.accountService = accountService;
        this.stageService = stageService;
    }

    @GetMapping(params = {AccountService.USERNAME_KEY, AccountService.PASSWORD_KEY, "timestamp"})
    public String index(@CurrentUser UserDetails userDetails,
                        @Param(AccountService.PASSWORD_KEY) String password,
                        @ModelAttribute IdCardForm form, Model model) {
        Account account = accountService.findByUserDetails(userDetails);
        model.addAttribute(AccountService.USERNAME_KEY, account.getUsername());
        model.addAttribute(AccountService.UID_KEY, account.getUid());
        model.addAttribute(AccountService.PASSWORD_KEY, password);
        if (account.getCard() == null) {
            return "account/id-card";
        }

        //noinspection DuplicatedCode
        Stage lastStage = Optional.ofNullable(account.getLastStage())
                .orElseGet(stageService::getNewest);
        model.addAttribute("lastStage", lastStage);
        List<Stage> stages = stageService.listByRecommend().stream()
                .filter(it -> !it.equals(lastStage))
                .collect(Collectors.toList());
        model.addAttribute("stages", stages);
        return "account/bookmark";
    }
}
