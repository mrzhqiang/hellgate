package hellgate.hub.script;

import com.google.common.base.Strings;
import hellgate.common.account.Account;
import hellgate.common.script.Script;
import hellgate.common.script.ScriptRepository;
import hellgate.hub.account.SimpleTokenService;
import hellgate.hub.account.TokenService;
import hellgate.hub.config.WebsiteProperties;
import org.springframework.context.MessageSource;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SimpleScriptService implements ScriptService {

    private static final String TOKEN_URL_TEMPLATE = "%s?access_token=%s";
    private static final String NAME_TEMPLATE = "%s[%s]";
    private static final String LABEL_TEMPLATE = "(%s)";

    private final WebsiteProperties properties;
    private final MessageSourceAccessor accessor;
    private final ScriptRepository repository;
    private final TokenService tokenService;

    public SimpleScriptService(WebsiteProperties properties,
                               MessageSource source,
                               ScriptRepository repository,
                               SimpleTokenService tokenService) {
        this.properties = properties;
        this.accessor = new MessageSourceAccessor(source);
        this.repository = repository;
        this.tokenService = tokenService;
    }

    @Override
    public ScriptData loadBy(Account account) {
        Script historyScript = account.getLastScript();
        Script latestScript = repository.findTopByActiveTrueAndTypeOrderByCreatedDesc(Script.Type.NEW)
                .map(it -> this.handleUrl(it, account))
                .orElse(null);
        List<Script> scriptList = repository.findAllByActiveTrueOrderByRecommendDescCreatedByDesc().stream()
                .map(it -> this.handleUrl(it, account))
                .collect(Collectors.toList());
        return ScriptData.of(historyScript, latestScript, scriptList);
    }

    private Script handleUrl(Script script, Account account) {
        if (script != null) {
            String url = script.getUrl();
            String token = this.tokenService.create(script.getName(), account.getUsername());
            script.setUrl(Strings.lenientFormat(TOKEN_URL_TEMPLATE, url, token));
            String name = Strings.lenientFormat(NAME_TEMPLATE, properties.getTitle(), script.getName());
            script.setName(name);
            if (Strings.isNullOrEmpty(script.getLabel())) {
                String messageCode = script.getType().getDescription();
                script.setLabel(Strings.lenientFormat(LABEL_TEMPLATE, accessor.getMessage(messageCode)));
            }
        }
        return script;
    }
}
