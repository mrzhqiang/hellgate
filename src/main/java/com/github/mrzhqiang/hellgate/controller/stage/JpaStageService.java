package com.github.mrzhqiang.hellgate.controller.stage;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JpaStageService implements StageService {

    private final StageRepository stageRepository;

    public JpaStageService(StageRepository stageRepository) {
        this.stageRepository = stageRepository;
    }

    public List<Stage> listByRecommend() {
        return stageRepository.findAll(Sort.by(Sort.Order.desc("recommend")));
    }
}
