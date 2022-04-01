package hellgate.api.controller.stage;

import hellgate.common.domain.stage.Stage;
import hellgate.common.domain.stage.StageRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StageService {

    private final StageRepository stageRepository;

    public StageService(StageRepository stageRepository) {
        this.stageRepository = stageRepository;
    }

    public List<Stage> listByRecommend() {
        return stageRepository.findAll(Sort.by(Sort.Order.desc("recommend")));
    }
}
