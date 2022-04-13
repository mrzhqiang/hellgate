package hellgate.common.script;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ScriptRepository extends JpaRepository<Script, Long> {

    List<Script> findAllByActiveTrueOrderByRecommendDescCreatedByDesc();

    Optional<Script> findTopByActiveTrueOrderByCreatedDesc();

}
