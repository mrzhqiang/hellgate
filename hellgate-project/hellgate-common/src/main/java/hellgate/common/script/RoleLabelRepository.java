package hellgate.common.script;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleLabelRepository extends JpaRepository<RoleLabel, Long> {

}
