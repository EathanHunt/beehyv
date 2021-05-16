package com.beehyv.assignment.service;

import com.beehyv.assignment.exceptions.InvalidExpressionException;
import com.beehyv.assignment.model.Expression;
import com.beehyv.assignment.model.ExpressionValue;
import com.beehyv.assignment.repository.ExpressionEvaluatorRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ExpressionEvaluatorService {

    private final ExpressionEvaluatorRepository repository;

    public ExpressionEvaluatorService(ExpressionEvaluatorRepository repository) {
        this.repository = repository;
    }

    public double evaluateExpression(String userId, Expression expression) throws InvalidExpressionException {
        try {
            ExpressionValue expressionValue = ExpressionEvaluatorUtil.evaluate(expression.getExpression());
            repository.addExpression(userId, expressionValue);
            return expressionValue.getValue();
        } catch (Exception e) {
            throw new InvalidExpressionException("Given expression is invalid, unable evaluate", e);
        }

    }

    public char getMostUserOperand(String userId) {
        List<ExpressionValue> expressions = repository.getExpressionValues(userId);
        char maxCountChar = Character.MIN_VALUE;
        if (expressions != null) {
            Map<Character, Integer> operandTotalCounts = getOperandTotalCounts(expressions);
            maxCountChar = getCharWithMaxCount(operandTotalCounts, maxCountChar);
        }
        return maxCountChar;
    }

    private Map<Character, Integer> getOperandTotalCounts(List<ExpressionValue> expressions) {
        Map<Character, Integer> totalOperandCounts = new HashMap<>();
        for (ExpressionValue expression : expressions) {
            for (Map.Entry<Character, Integer> operandCount : expression.getOperandCounts().entrySet()) {
                int newCount = operandCount.getValue() + totalOperandCounts.computeIfAbsent(operandCount.getKey(), k -> 0);
                totalOperandCounts.put(operandCount.getKey(), newCount);
            }
        }
        return totalOperandCounts;
    }

    private char getCharWithMaxCount(Map<Character, Integer> totalOperandCounts, char defaultValue) {
        char maxCountChar = defaultValue;
        int maxCount = 0;
        for (Map.Entry<Character, Integer> count : totalOperandCounts.entrySet()) {
            if (maxCount < count.getValue()) {
                maxCount = count.getValue();
                maxCountChar = count.getKey();
            }
        }
        return maxCountChar;
    }
}
