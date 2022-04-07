package hellgate.common.model.script;

import hellgate.common.model.AuditableEntity;
import hellgate.common.model.stage.Stage;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.List;

/**
 * 剧本。
 * <p>
 * 专属于个人的剧本，包含对应的舞台，以及玩家在剧本中创建的角色。
 */
@Getter
@Setter
@ToString(callSuper = true)
@Entity
public class Script extends AuditableEntity {

    @ManyToOne(cascade = CascadeType.ALL, optional = false)
    private Stage stage;

    @OneToMany(cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<Actor> actors;
}
