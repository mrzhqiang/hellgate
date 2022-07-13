package hellgate.hub.account;

import com.google.common.base.Strings;
import hellgate.common.account.AccountForm;
import hellgate.hub.config.HubSecurityProperties;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.Instant;

@Controller
@RequestMapping("/register")
public class RegisterController {

    private static final String BOOKMARK_TEMPLATE = "%s?" + HubAccountService.USERNAME_KEY
            + "=%s&" + HubAccountService.PASSWORD_KEY + "=%s&timestamp=%s";

    private final HubAccountService hubAccountService;
    private final HubSecurityProperties hubSecurityProperties;

    public RegisterController(HubAccountService hubAccountService,
                              HubSecurityProperties hubSecurityProperties) {
        this.hubAccountService = hubAccountService;
        this.hubSecurityProperties = hubSecurityProperties;
    }

    @GetMapping
    public String index(@ModelAttribute AccountForm form) {
        return "account/register";
    }

    @PostMapping
    public String register(@Validated @ModelAttribute AccountForm form, BindingResult result) {
        if (result.hasErrors()) {
            return "account/register";
        }

        boolean registerSuccessful = hubAccountService.register(form);
        if (!registerSuccessful) {
            result.reject("RegisterController.failed");
            return "account/register";
        }

        String bookmarkPath = hubSecurityProperties.getBookmarkPath();
        String successUrl = Strings.lenientFormat(BOOKMARK_TEMPLATE,
                bookmarkPath, form.getUsername(), form.getPassword(), Instant.now().getEpochSecond());
        return "redirect:" + successUrl;
    }

    @GetMapping("/help")
    public String help() {
        return "account/register-help";
    }
}
