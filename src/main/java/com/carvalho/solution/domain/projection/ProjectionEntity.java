package com.carvalho.solution.domain.projection;


import com.carvalho.solution.domain.base.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;


@Entity
@Table(name = "projection")
@Data
@EqualsAndHashCode(callSuper=false)
public class ProjectionEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "category_input")
    private String inputCategory;

    @Column(name = "category_output")
    private String outputCategory;

    @Column(name = "due_date", nullable = false)
    private LocalDateTime dueDate;

    @Column(name = "due_days")
    private int dueDays;

    @Column(name = "config_date", nullable = false)
    private LocalDateTime configDate;

    @Column(name = "active_flag")
    private Boolean activeFlag = true;

    @Column(name = "tenant_id", nullable = false)
    private Long tenantId;

}
