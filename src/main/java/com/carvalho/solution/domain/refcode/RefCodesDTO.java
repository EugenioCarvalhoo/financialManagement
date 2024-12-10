package com.carvalho.solution.domain.refcode;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
    @NotNull
    private boolean whitTenantId;
    private Boolean activeFlag;
    // private Boolean required;
}
