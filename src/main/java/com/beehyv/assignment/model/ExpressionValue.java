package com.beehyv.assignment.model;

import javax.crypto.Mac;
import java.util.Map;

public class ExpressionValue {
    private double                  value;
    private Map<Character, Integer> operandCounts;

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public Map<Character, Integer> getOperandCounts() {
        return operandCounts;
    }

    public void setOperandCounts(Map<Character, Integer> operandCounts) {
        this.operandCounts = operandCounts;
    }
}
