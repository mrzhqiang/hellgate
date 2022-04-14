package hellgate.common.script;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.stream.Stream;

@Repository
public interface ScriptRepository extends JpaRepository<Script, Long> {

    Stream<Script> findAllByActiveTrueOrderByRecommendDescCreatedByDesc();

    Optional<Script> findTopByActiveTrueOrderByCreatedDesc();
}
