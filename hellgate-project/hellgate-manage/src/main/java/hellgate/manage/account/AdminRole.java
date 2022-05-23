package hellgate.manage.account;

import hellgate.common.audit.AuditableEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Column;
import javax.persistence.Entity;

@Getter
@Setter
@ToString(callSuper = true)
@Entity
public class AdminRole extends AuditableEntity  implements GrantedAuthority {

    @Column(unique = true, nullable = false)
    private String name;

    @Override
    public String getAuthority() {
        return name;
    }
}
