package com.github.mrzhqiang.hellgate.stage;

import com.github.mrzhqiang.hellgate.domain.AuditableEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * 舞台。
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Entity
public class Stage extends AuditableEntity {

    @Id
    private String name;
    private String label;
    /**
     * 推荐指数：1 -- 100，default 1
     */
    private Integer recommend = 1;
    /**
     * 热门积分：1000 * (在线人数 / (注册人数 + 1))
     */
    private Integer popularity = 0;
}
