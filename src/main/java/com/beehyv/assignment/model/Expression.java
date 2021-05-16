package com.beehyv.assignment.model;

import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.NotBlank;

public class Expression {
    @NotBlank(message = "Expression is mandatory")
    @Schema(example = "9*(4-1)/(2+1)", description = "Arithmetic expression to be evalauated")
    private String expression;

    public Expression() {
    }

    public Expression(String expression) {
        this.expression = expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public String getExpression() {
        return expression;
    }


}
