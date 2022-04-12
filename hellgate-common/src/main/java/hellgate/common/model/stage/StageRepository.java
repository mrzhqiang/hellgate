package hellgate.common.model.stage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StageRepository extends JpaRepository<Stage, Long> {

    List<Stage> findAllByActiveTrueOrderByRecommendDescCreatedByDesc();

    Optional<Stage> findTopByActiveTrueOrderByCreatedDesc();
}
