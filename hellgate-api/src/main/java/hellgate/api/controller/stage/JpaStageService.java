package hellgate.api.controller.stage;

import hellgate.common.model.stage.Stage;
import hellgate.common.model.stage.StageRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JpaStageService implements StageService {

    private final StageRepository stageRepository;

    public JpaStageService(StageRepository stageRepository) {
        this.stageRepository = stageRepository;
    }

    @Override
    public List<Stage> listByRecommend() {
        return stageRepository.findAllByActiveTrueOrderByRecommendDescCreatedByDesc();
    }

    @Override
    public Stage getNewest() {
        return stageRepository.findTopByActiveTrueOrderByCreatedDesc().orElse(null);
    }
}
