package com.github.mrzhqiang.hellgate.stage;

import com.github.mrzhqiang.hellgate.account.Account;
import com.github.mrzhqiang.hellgate.account.AccountService;
import com.github.mrzhqiang.hellgate.script.Script;
import com.github.mrzhqiang.hellgate.util.HttpSessions;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/stage")
public class StageController {

    private final AccountService accountService;
    private final StageService stageService;

    public StageController(AccountService accountService, StageService stageService) {
        this.accountService = accountService;
        this.stageService = stageService;
    }

    @GetMapping
    public String index(Model model) {
        List<Script> scripts = HttpSessions.ofUsername()
                .flatMap(accountService::find)
                .map(Account::getScripts)
                .orElse(Collections.emptyList());
        model.addAttribute("scripts", scripts);
        List<Stage> stages = stageService.listByRecommend();
        model.addAttribute("stages", stages);
        return "/user/index";
    }
}
