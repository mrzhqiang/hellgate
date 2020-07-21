package com.github.mrzhqiang.hellgateapi.model.entity;

import lombok.Data;

import javax.persistence.Embeddable;

@Data
@Embeddable
public class TypedValue {
    /**
     * 类型：
     * 1 数值
     * 2 百分比
     */
    private Integer type;
    /**
     * 方向：
     * 0 固定值
     * > 0 按等级增长
     * < 0 按等级减少
     */
    private Integer direction;
    private Integer value;
}
