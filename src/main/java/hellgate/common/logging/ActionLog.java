package hellgate.common.logging;

import hellgate.common.domain.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@ToString(callSuper = true)
@Entity
public class ActionLog extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 操作名称
     */
    private String action;
    /**
     * 操作类型
     */
    @Enumerated(EnumType.STRING)
    private ActionType type;
    /**
     * 操作所在类
     */
    private String target;
    /**
     * 操作所在方法
     */
    private String method;
    /**
     * 操作所在方法参数
     */
    private String params;
    /**
     * 操作状态
     */
    @Enumerated(EnumType.STRING)
    private ActionState state;
    /**
     * 操作返回结果
     */
    private String result;
    /**
     * 操作人的用户名称
     */
    private String operator;
    /**
     * 操作人的客户端 IP 地址
     */
    private String ip;
    /**
     * 操作人所在地区（由 IP 地址获得）
     */
    private String address;
}
