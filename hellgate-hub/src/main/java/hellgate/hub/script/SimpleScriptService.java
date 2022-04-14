package hellgate.hub.script;

import com.google.common.base.Strings;
import hellgate.common.account.Account;
import hellgate.common.script.Script;
import hellgate.common.script.ScriptRepository;
import hellgate.hub.token.SimpleTokenService;
import hellgate.hub.token.TokenService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SimpleScriptService implements ScriptService {

    private static final String TOKEN_URL_TEMPLATE = "%s?access_token=%s";

    private final ScriptRepository repository;
    private final TokenService tokenService;

    public SimpleScriptService(ScriptRepository repository,
                               SimpleTokenService tokenService) {
        this.repository = repository;
        this.tokenService = tokenService;
    }

    @Override
    public ScriptData loadBy(Account account) {
        Script historyScript = account.getHistoryScript();
        Script latestScript = repository.findTopByActiveTrueOrderByCreatedDesc()
                .map(it -> this.handleUrl(it, account))
                .orElse(null);
        List<Script> scriptList = repository.findAllByActiveTrueOrderByRecommendDescCreatedByDesc()
                .map(it -> this.handleUrl(it, account))
                .collect(Collectors.toList());
        return ScriptData.of(historyScript, latestScript, scriptList);
    }

    private Script handleUrl(Script script, Account account) {
        if (script != null) {
            String url = script.getUrl();
            String token = this.tokenService.create(script.getName(), account.getUsername());
            script.setUrl(Strings.lenientFormat(TOKEN_URL_TEMPLATE, url, token));
        }
        return script;
    }
}
