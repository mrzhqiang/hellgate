package com.github.mrzhqiang.hellgate.stage;

import com.github.mrzhqiang.hellgate.account.User;
import com.github.mrzhqiang.hellgate.script.Script;
import com.github.mrzhqiang.hellgate.util.Authentications;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/stage")
@RequiredArgsConstructor
public class StageController {

    private final StageService stageService;

    @GetMapping
    public String index(Model model) {
        List<Stage> stages = stageService.listByRecommend();
        model.addAttribute("stages", stages);
        Authentications.currentUser()
                .map(User::getLastScript)
                .map(Script::getStage)
                .ifPresent(it -> model.addAttribute("lastStage", it));
        return "/stage/index";
    }
}
