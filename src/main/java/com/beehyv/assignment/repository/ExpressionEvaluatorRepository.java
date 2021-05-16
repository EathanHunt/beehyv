package com.beehyv.assignment.repository;

import com.beehyv.assignment.model.ExpressionValue;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ExpressionEvaluatorRepository {
    private final Map<String, List<ExpressionValue>> userExpressions = new HashMap<>();

    public void addExpression(String userId, ExpressionValue expression) {
        List<ExpressionValue> expressions = userExpressions.computeIfAbsent(userId, k -> new ArrayList<>());
        expressions.add(expression);
    }

    public List<ExpressionValue> getExpressionValues(String userId) {
        return userExpressions.get(userId);
    }
}
