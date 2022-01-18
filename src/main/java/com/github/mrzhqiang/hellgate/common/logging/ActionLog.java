package com.github.mrzhqiang.hellgate.common.logging;

import com.github.mrzhqiang.hellgate.common.domain.BaseIdSoftDeleteAuditableEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.SQLDelete;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import java.time.Duration;

@Getter
@Setter
@ToString(callSuper = true)
@Entity
@Table(name = "action_log")
@SQLDelete(sql = "update action_log set deleted = true where id = ?")
public class ActionLog extends BaseIdSoftDeleteAuditableEntity {

    private String action;
    @Enumerated(EnumType.STRING)
    private ActionType type;
    private String target;
    private String method;
    private String params;
    private Duration elapsed;
}
