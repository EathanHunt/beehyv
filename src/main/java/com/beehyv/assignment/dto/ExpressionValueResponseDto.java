package com.beehyv.assignment.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public class ExpressionValueResponseDto {
    @Schema(description = "Calculated value of the expression")
    private Double value;

    public ExpressionValueResponseDto() {
    }

    public ExpressionValueResponseDto(double value) {
        this.value = value;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }
}
