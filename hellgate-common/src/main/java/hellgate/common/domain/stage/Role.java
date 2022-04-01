package hellgate.common.domain.stage;

import hellgate.common.domain.BaseAuditableEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@ToString(callSuper = true)
@Entity
public class Role extends BaseAuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private Integer type;

    @ManyToMany(cascade = CascadeType.ALL)
    @ToString.Exclude
    private Set<Magic> magics;
}
