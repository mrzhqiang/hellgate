package hellgate.api.controller.stage;

import hellgate.common.annotation.CurrentUser;
import hellgate.common.model.account.Account;
import hellgate.common.model.script.Script;
import hellgate.common.model.stage.Stage;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/stage")
public class StageController {

    private final StageService stageService;

    public StageController(StageService stageService) {
        this.stageService = stageService;
    }

    @GetMapping
    public String index(@CurrentUser Account account, Model model) {
        List<Stage> stages = stageService.listByRecommend();
        model.addAttribute("stages", stages);
        Optional.ofNullable(account)
                .map(Account::getLastScript)
                .map(Script::getStage)
                .ifPresent(it -> model.addAttribute("lastStage", it));
        return "/stage/index";
    }
}