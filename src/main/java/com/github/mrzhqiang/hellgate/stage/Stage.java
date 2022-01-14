package com.github.mrzhqiang.hellgate.stage;

import com.github.mrzhqiang.hellgate.common.domain.BaseAuditableEntity;
import com.github.mrzhqiang.hellgate.script.Script;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
public class Stage extends BaseAuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String label;
    private boolean active = true;
    /**
     * 推荐指数：1 -- 100，default 1
     */
    private Integer recommend = 1;
    /**
     * 热门积分：1000 * (在线人数 / (注册人数 + 1))
     */
    private Integer popularity = 0;

    @OneToMany
    @JoinColumn(foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Set<Role> roles;

    @OneToMany
    @JoinColumn(foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private List<Script> actives;
}
