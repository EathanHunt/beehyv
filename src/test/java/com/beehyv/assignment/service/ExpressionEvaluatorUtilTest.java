package com.beehyv.assignment.service;

import com.beehyv.assignment.exceptions.InvalidExpressionException;
import com.beehyv.assignment.model.ExpressionValue;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ExpressionEvaluatorUtilTest {
    @Test
    void testForDoubleValues() {
        ExpressionValue expressionValue = ExpressionEvaluatorUtil.evaluate("10.1*(4.5+0.5)/.5");

        assertEquals(101, expressionValue.getValue());
        assertEquals(1, expressionValue.getOperandCounts().get('*'));
        assertEquals(1, expressionValue.getOperandCounts().get('+'));
        assertEquals(1, expressionValue.getOperandCounts().get('/'));
        assertNull(expressionValue.getOperandCounts().get('-'));
    }
}