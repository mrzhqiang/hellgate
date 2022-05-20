package hellgate.common.script;

import hellgate.common.audit.AuditableEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.List;

@Getter
@Setter
@ToString(callSuper = true)
@Entity
public class Role extends AuditableEntity {

    private String name;
    private String i18nName;
    private Integer code;

    private Integer initHp;
    private Integer initMp;

    @ManyToOne
    private Script script;

    @OneToMany(mappedBy = "role")
    @ToString.Exclude
    private List<RoleLabel> labels;

    @ManyToMany
    @ToString.Exclude
    private List<Skill> skills;
}
