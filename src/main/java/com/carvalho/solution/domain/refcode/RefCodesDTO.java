package com.carvalho.solution.domain.refcode;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RefCodesDTO {
    private Long id;
    @NotBlank
    private String type;
    @NotBlank
    private String code;
    @NotBlank
    private String name;
    private String description;
    private boolean whitTenantId = false; 
    // private Boolean required;
    // private Boolean activeFlag;
}
