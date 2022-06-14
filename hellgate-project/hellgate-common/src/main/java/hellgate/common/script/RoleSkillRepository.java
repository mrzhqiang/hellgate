package hellgate.common.script;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleSkillRepository extends JpaRepository<RoleSkill, Long> {

}
