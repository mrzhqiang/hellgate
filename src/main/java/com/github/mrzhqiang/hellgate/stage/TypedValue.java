package com.github.mrzhqiang.hellgate.stage;

import com.github.mrzhqiang.hellgate.domain.SoftDeleteEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.annotation.Nullable;
import javax.persistence.*;
import java.math.BigDecimal;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
public class TypedValue extends SoftDeleteEntity {

    /**
     * 固定值。
     * <p>
     * return value
     */
    public static final int TYPE_FIXED_NUMBER = 1;
    /**
     * 因数。
     * <p>
     * return var * (1 + value)
     */
    public static final int TYPE_FACTOR_NUMBER = 2;
    /**
     * 固定百分比。
     * <p>
     * return (value / 100)
     */
    public static final int TYPE_FIXED_PERCENTAGE = 3;
    /**
     * 因数百分比。
     * <p>
     * return var * (1 + value / 100)
     */
    public static final int TYPE_FACTOR_PERCENTAGE = 4;

    public static final BigDecimal PERCENT = BigDecimal.valueOf(100);

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @Column(nullable = false)
    private Integer type;

    @Column(nullable = false)
    private BigDecimal value = BigDecimal.ZERO;

    public BigDecimal count(@Nullable BigDecimal var) {
        if (var == null) {
            var = BigDecimal.ZERO;
        }

        switch (type) {
            case TYPE_FIXED_NUMBER:
                return value;
            case TYPE_FACTOR_NUMBER:
                return var.multiply(BigDecimal.ONE.add(value));
            case TYPE_FIXED_PERCENTAGE:
                // ROUND_HALF_EVEN 的表现为：< 0.5 舍弃，> 0.5 进位，= 0.5 看左边的数是否为奇数，奇数进位，偶数舍弃。
                // 据说这种方式可以最大限度保留计算精度，避免累积误差。
                return value.divide(PERCENT, BigDecimal.ROUND_HALF_EVEN);
            case TYPE_FACTOR_PERCENTAGE:
                return var.multiply(BigDecimal.ONE.add(value.divide(PERCENT, BigDecimal.ROUND_HALF_EVEN)));
            default:
                return var;
        }
    }
}
