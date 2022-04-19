package hellgate.admin.account;

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
public class AdminRole extends AuditableEntity {

    private String name;

    @ManyToMany
    @ToString.Exclude
    private List<AdminRole> containers;
}
