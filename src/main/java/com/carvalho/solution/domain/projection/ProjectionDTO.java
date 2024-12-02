package com.carvalho.solution.domain.projection;

import com.carvalho.solution.domain.tenant.TenantDTO;


import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ProjectionDTO {

    private Long id;

    private String inputCategory;

    private String outputCategory;

    private LocalDateTime dueDate;

    private int dueDays;

    private LocalDateTime configDate;

    private Boolean activeFlag = true;

    private Long tenantId;

}
