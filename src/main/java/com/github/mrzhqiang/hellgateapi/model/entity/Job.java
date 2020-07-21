package com.github.mrzhqiang.hellgateapi.model.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "job")
public class Job extends BaseIdEntity {
    private String name;
    private Integer type;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Magic> magics;
}
