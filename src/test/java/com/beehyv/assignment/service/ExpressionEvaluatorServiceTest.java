package com.beehyv.assignment.service;

import com.beehyv.assignment.exceptions.InvalidExpressionException;
import com.beehyv.assignment.model.Expression;
import com.beehyv.assignment.repository.ExpressionEvaluatorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ExpressionEvaluatorServiceTest {

    private ExpressionEvaluatorService service;

    @BeforeEach
    void setUp() {
        ExpressionEvaluatorRepository repository = new ExpressionEvaluatorRepository();
        service = new ExpressionEvaluatorService(repository);
    }

    @Test
    void testValueForValidExpression() throws InvalidExpressionException {
        Expression expression = new Expression("5*(3+(1-5))/5");

        Object value = service.evaluateExpression("user1", expression);

        assertEquals(-1.0, value);
    }

    @Test
    void testThrowsExceptionForDivideWithZero() {
        Expression expression = new Expression("5*(3+(1-5))/0");

        InvalidExpressionException exception = assertThrows(InvalidExpressionException.class,
                () -> service.evaluateExpression("user1", expression));
        assertEquals("Given expression is invalid, unable evaluate", exception.getMessage());
    }

    @Test
    void testThrowsExceptionForInvalidExpression() {
        Expression expression = new Expression("5*(3+(1-5)/7");

        InvalidExpressionException exception = assertThrows(InvalidExpressionException.class,
                () -> service.evaluateExpression("user1", expression));
        assertEquals("Given expression is invalid, unable evaluate", exception.getMessage());
    }

    @Test
    void testThrowsExceptionForWithInvalidCharacter() {
        Expression expression = new Expression("5*(3+(1-5))a/7");

        InvalidExpressionException exception = assertThrows(InvalidExpressionException.class,
                () -> service.evaluateExpression("user1", expression));
        assertEquals("Given expression is invalid, unable evaluate", exception.getMessage());
    }

    @Test
    void testMaxOperandCountReturnsCorrectValue() throws InvalidExpressionException {
        Expression expression1 = new Expression("5*(3+(1-5))/7");
        Expression expression2 = new Expression("5*(3+(1+5))");
        service.evaluateExpression("user1", expression1);
        service.evaluateExpression("user1", expression2);
        service.evaluateExpression("user2", expression1);

        char user1Char = service.getMostUserOperand("user1");
        char user2Char = service.getMostUserOperand("user2");

        assertEquals('+', user1Char);
        assertEquals('*', user2Char);
    }

    @Test
    void testMostOperandCountReturnsEmptyForInvalidUser() throws InvalidExpressionException {
        Expression expression1 = new Expression("5*(3+(1-5))/7");
        Expression expression2 = new Expression("5*(3+(1+5))");
        service.evaluateExpression("user1", expression1);
        service.evaluateExpression("user1", expression2);

        char user1Char = service.getMostUserOperand("user1");
        char user2Char = service.getMostUserOperand("user2");

        assertEquals('+', user1Char);
        assertEquals('\u0000', user2Char);
    }
}