package hellgate.common.stage;

import hellgate.common.audit.AuditableEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

/**
 * 舞台。
 * <p>
 * 一般表示为不同的服务器。
 */
@Getter
@Setter
@ToString(callSuper = true)
@Entity
public class Stage extends AuditableEntity {

    @OneToMany(cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<Actor> actors;
}
