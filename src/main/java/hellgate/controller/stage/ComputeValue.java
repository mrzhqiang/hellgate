package hellgate.controller.stage;

import hellgate.common.domain.BaseAuditableEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
public class ComputeValue extends BaseAuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String name;
}
