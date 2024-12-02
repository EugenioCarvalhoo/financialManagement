package com.carvalho.solution.domain.refcode;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RefCodesFieldType {
    INPUT_ENTITY("INPUT_ENTITY"),
    OUTPUT_ENTITY("OUTPUT_ENTITY");

    private final String type;
}
