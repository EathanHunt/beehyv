package com.beehyv.assignment.service;

import com.beehyv.assignment.model.ExpressionValue;

import java.util.HashMap;
import java.util.Stack;


public class ExpressionEvaluatorUtil {

    private static final char ADD_OPERAND      = '+';
    private static final char SUBTRACT_OPERAND = '-';
    private static final char MULTIPLY_OPERAND = '*';
    private static final char DIVIDE_OPERAND   = '/';

    public static ExpressionValue evaluate(String expression) {
        char[] tokens = expression.toCharArray();
        Stack<Double> values = new Stack<>();
        Stack<Character> operands = new Stack<>();
        HashMap<Character, Integer> operandCount = new HashMap<>();
        int index = 0;
        while (index < tokens.length) {
            char token = tokens[index];
            if (token == ' ') {
                index++;
                continue;
            }
            if (isNumericalCharacter(token)) {
                StringBuilder number = new StringBuilder();
                while (index < tokens.length && isNumericalCharacter(tokens[index])) {
                    number.append(tokens[index++]);
                }
                values.push(Double.parseDouble(number.toString()));
                index--;
            } else if (token == '(') {
                operands.push(token);
            } else if (token == ')') {
                while (operands.peek() != '(') {
                    evaluateOperand(values, operands, operandCount);
                }
                operands.pop();
            } else if (isArithmeticOperand(token)) {
                while (!operands.empty() && hasPrecedence(token, operands.peek())) {
                    evaluateOperand(values, operands, operandCount);
                }
                operands.push(token);
            } else {
                throw new UnsupportedOperationException("The provided character "+ token + " is not supported");
            }
            index++;
        }
        while (!operands.empty()) {
            evaluateOperand(values, operands, operandCount);
        }
        return createExpression(values.pop(), operandCount);
    }

    private static void incrementCount(HashMap<Character, Integer> operandCount, Character operand) {
        operandCount.put(operand, operandCount.computeIfAbsent(operand, v -> 0) + 1);
    }

    private static boolean isArithmeticOperand(char token) {
        return token == ADD_OPERAND || token == SUBTRACT_OPERAND || token == MULTIPLY_OPERAND || token == DIVIDE_OPERAND;
    }

    private static boolean isNumericalCharacter(char token) {
        return token >= '0' && token <= '9' || token == '.';
    }

    private static boolean hasPrecedence(char op1, char op2) {
        if (op2 == '(' || op2 == ')') {
            return false;
        } else {
            return (op1 != MULTIPLY_OPERAND && op1 != DIVIDE_OPERAND) || (op2 != ADD_OPERAND && op2 != SUBTRACT_OPERAND);
        }
    }

    private static double applyOperand(char operand, double b, double a) {
        switch (operand) {
            case ADD_OPERAND:
                return a + b;
            case SUBTRACT_OPERAND:
                return a - b;
            case MULTIPLY_OPERAND:
                return a * b;
            case DIVIDE_OPERAND:
                if (b == 0) {
                    throw new UnsupportedOperationException("Cannot divide by zero");
                }
                return a / b;
        }
        return 0;
    }

    private static void evaluateOperand(Stack<Double> values, Stack<Character> operands, HashMap<Character, Integer> operandCount) {
        Character operand = operands.pop();
        incrementCount(operandCount, operand);
        values.push(applyOperand(operand, values.pop(), values.pop()));
    }

    private static ExpressionValue createExpression(double value, HashMap<Character, Integer> operandCount) {
        ExpressionValue expressionValue = new ExpressionValue();
        expressionValue.setOperandCounts(operandCount);
        expressionValue.setValue(value);
        return expressionValue;
    }
}
