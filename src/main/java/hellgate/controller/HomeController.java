package hellgate.controller;

import hellgate.common.Authentications;
import org.springframework.security.core.Authentication;
import org.springframework.session.FindByIndexNameSessionRepository;
import org.springframework.session.Session;
import org.springframework.session.data.redis.RedisIndexedSessionRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Collections;
import java.util.Map;

/**
 * 主页控制器。
 */
@Controller
public class HomeController {

    private final FindByIndexNameSessionRepository<? extends Session> sessionRepository;

    public HomeController(RedisIndexedSessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    @GetMapping("/")
    public String index(Model model) {
        Map<String, ? extends Session> sessionMap = Authentications.ofCurrent()
                .map(Authentication::getPrincipal)
                .map(String::valueOf)
                .map(sessionRepository::findByPrincipalName)
                .orElse(Collections.emptyMap());
        model.addAttribute("sessionMap", sessionMap);
        return "index";
    }
}
