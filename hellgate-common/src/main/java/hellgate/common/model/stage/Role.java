package hellgate.common.model.stage;

import hellgate.common.model.AuditableEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToMany;
import java.math.BigDecimal;
import java.util.List;

/**
 * 职业。
 * <p>
 * 内置职业：魔法师、战士、弓箭手。
 */
@Getter
@Setter
@ToString(callSuper = true)
@Entity
public class Role extends AuditableEntity {

    private String name;
    @Enumerated(EnumType.STRING)
    private Type type = Type.R1_WARRIOR;

    private BigDecimal initHp;
    private BigDecimal initMp;

    @ManyToMany(cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<Magic> magics;

    /**
     * 职业类型。
     */
    public enum Type {
        R1_WARRIOR("战士"),
        R1_MAGE("魔法师"),
        R1_ARCHER("弓箭手"),
        ;

        final String name;

        Type(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }
    }
}
