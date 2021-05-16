package com.beehyv.assignment.model;

import javax.validation.constraints.NotBlank;

public class Expression {
    @NotBlank(message = "Expression is mandatory")
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
