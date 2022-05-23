package hellgate.manage.account;

import hellgate.common.audit.AuditableEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import java.util.List;

@Getter
@Setter
@ToString(callSuper = true)
@Entity
public class AdminGroup extends AuditableEntity {

    private String name;
    private String alias;

    @ManyToMany(mappedBy = "groups")
    @ToString.Exclude
    private List<AdminAccount> members;
    @ManyToMany
    @ToString.Exclude
    private List<AdminRole> roles;
}
