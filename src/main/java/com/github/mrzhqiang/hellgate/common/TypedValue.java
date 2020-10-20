package com.github.mrzhqiang.hellgate.common;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
public class TypedValue extends BaseIdEntity {

    public static final int TYPE_FIXED_NUMBER = 1;
    public static final int TYPE_FACTOR_NUMBER = 2;
    public static final int TYPE_FIXED_PERCENTAGE = 3;
    public static final int TYPE_FACTOR_PERCENTAGE = 4;

    /**
     * 类型：
     * 1 固定数值
     * 2 因子数值
     * 3 固定百分比
     * 4 因子百分比
     */
    private Integer type;

    /**
     * 具体的数值。
     */
    private Integer value;

    /**
     * 按相关值计算具体数值。
     * <p>
     * 比如：
     * type == 1；则 return == value
     * type == 4；则 return == Math.max(0, (1 + var) * value / 100)
     */
    public long computer(Integer var) {
        switch (type) {
            case TYPE_FIXED_NUMBER:
                return value;
            case TYPE_FACTOR_NUMBER:
                return Math.max(0, (1 + var) * value);
            case TYPE_FIXED_PERCENTAGE:
                return value / 100;
            case TYPE_FACTOR_PERCENTAGE:
                return Math.max(0, (1 + var) * value / 100);
            default:
                return 0;
        }
    }
}
