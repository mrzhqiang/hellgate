package hellgate.api.controller.stage;

import hellgate.common.domain.stage.Stage;
import hellgate.common.domain.account.Account;
import hellgate.common.annotation.CurrentUser;
import hellgate.common.domain.script.Script;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.session.FindByIndexNameSessionRepository;
import org.springframework.session.Session;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Controller
@RequestMapping("/stage")
public class StageController {

    private final StageService stageService;

    public StageController(StageService stageService) {
        this.stageService = stageService;
    }
    /*

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
    */

    @RequestMapping("/setValue")
    public String setValue(@RequestParam(name = "key", required = false) String key,
                           @RequestParam(name = "value", required = false) String value, HttpServletRequest request) {
        if (!ObjectUtils.isEmpty(key) && !ObjectUtils.isEmpty(value)) {
            request.getSession().setAttribute(key, value);
        }
        return "/stage/index";
    }


    // tag::findbyusername[]
    @Autowired
    FindByIndexNameSessionRepository<? extends Session> sessions;

    @GetMapping
    public String index(Principal principal, Model model) {
        Collection<? extends Session> usersSessions = this.sessions.findByPrincipalName(principal.getName()).values();
        model.addAttribute("sessions", usersSessions);
        return "/stage/index";
    }
    // end::findbyusername[]

    @PostMapping("/sessions/{sessionIdToDelete}")
    public String removeSession(Principal principal, @PathVariable String sessionIdToDelete) {
        Set<String> usersSessionIds = this.sessions.findByPrincipalName(principal.getName()).keySet();
        if (usersSessionIds.contains(sessionIdToDelete)) {
            this.sessions.deleteById(sessionIdToDelete);
        }

        return "redirect:/stage";
    }

}
