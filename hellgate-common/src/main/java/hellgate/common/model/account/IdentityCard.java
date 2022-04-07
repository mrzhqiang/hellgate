package hellgate.common.model.account;

import hellgate.common.model.AuditableEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

@Getter
@Setter
@ToString(callSuper = true)
@Entity
public class IdentityCard extends AuditableEntity {

    @Column(nullable = false, unique = true)
    private String number;
    @Column(nullable = false)
    private String fullName;

    @OneToMany(mappedBy = "card")
    @ToString.Exclude
    private List<Account> holders;
}
