package hellgate.api.controller.session;

import org.springframework.session.FindByIndexNameSessionRepository;
import org.springframework.session.Session;
import org.springframework.session.data.redis.RedisIndexedSessionRepository;
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
import java.util.Set;

/**
 * 会话控制器，这是一个 Demo 类。
 */
@Controller
@RequestMapping("/session")
public class SessionController {

    private final FindByIndexNameSessionRepository<? extends Session> sessions;

    public SessionController(RedisIndexedSessionRepository sessions) {
        this.sessions = sessions;
    }

    @GetMapping
    public String index(Principal principal, Model model) {
        Collection<? extends Session> usersSessions = this.sessions.findByPrincipalName(principal.getName()).values();
        model.addAttribute("sessions", usersSessions);
        return "/session/index";
    }

    @PostMapping("/{sessionIdToDelete}")
    public String removeSession(Principal principal, @PathVariable String sessionIdToDelete) {
        Set<String> usersSessionIds = this.sessions.findByPrincipalName(principal.getName()).keySet();
        if (usersSessionIds.contains(sessionIdToDelete)) {
            this.sessions.deleteById(sessionIdToDelete);
        }

        return "redirect:/session";
    }

    @RequestMapping("/setValue")
    public String setValue(@RequestParam(name = "key", required = false) String key,
                           @RequestParam(name = "value", required = false) String value, HttpServletRequest request) {
        if (!ObjectUtils.isEmpty(key) && !ObjectUtils.isEmpty(value)) {
            request.getSession().setAttribute(key, value);
        }
        return "/session/index";
    }
}
