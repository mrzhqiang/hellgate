package hellgate.controller.init;

import hellgate.controller.account.AccountService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/init")
public class InitializationController {

    private final AccountService accountService;

    public InitializationController(AccountService accountService) {
        this.accountService = accountService;
    }
}
