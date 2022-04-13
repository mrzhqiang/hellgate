package hellgate.hub.script;

import hellgate.common.script.Script;
import hellgate.common.script.ScriptRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.util.List;

@Service
public class JpaScriptService implements ScriptService {

    private final ScriptRepository repository;

    public JpaScriptService(ScriptRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Script> listByRecommend() {
        return repository.findAllByActiveTrueOrderByRecommendDescCreatedByDesc();
    }

    @Nullable
    @Override
    public Script getNewest() {
        return repository.findTopByActiveTrueOrderByCreatedDesc().orElse(null);
    }
}
