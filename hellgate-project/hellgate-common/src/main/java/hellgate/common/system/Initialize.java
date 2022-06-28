package hellgate.common.system;

import hellgate.common.domain.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;

@Getter
@Setter
@ToString(callSuper = true)
@Entity
public class Initialize extends BaseEntity {
}
