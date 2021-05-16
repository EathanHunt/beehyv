package com.beehyv.assignment.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public class MostUsedOperandDto {
    @Schema(allowableValues = {"*", "/", "+", "-", "\u0000"},
            description = "Value is \"*\", \"/\", \"+\", \"-\" when operand is available otherwise it is \"\u0000\"")
    private char operand;

    public MostUsedOperandDto() {
    }

    public MostUsedOperandDto(char operand) {
        this.operand = operand;
    }

    public char getOperand() {
        return operand;
    }

    public void setOperand(char operand) {
        this.operand = operand;
    }
}
