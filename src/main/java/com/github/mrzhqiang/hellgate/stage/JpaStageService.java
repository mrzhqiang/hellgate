package com.github.mrzhqiang.hellgate.stage;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class JpaStageService implements StageService {

    private final StageRepository stageRepository;

    public List<Stage> listByRecommend() {
        return stageRepository.findAll(Sort.by(Sort.Order.desc("recommend")));
    }

}
