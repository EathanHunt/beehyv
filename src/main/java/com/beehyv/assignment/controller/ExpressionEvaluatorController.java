package com.beehyv.assignment.controller;

import com.beehyv.assignment.exceptions.InvalidExpressionException;
import com.beehyv.assignment.model.Expression;
import com.beehyv.assignment.service.ExpressionEvaluatorService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
public class ExpressionEvaluatorController {

    private final ExpressionEvaluatorService service;

    public ExpressionEvaluatorController(ExpressionEvaluatorService service) {
        this.service = service;
    }


    @PostMapping("/evaluate/{userId}")
    @Operation(summary = "Returns the calculated value for the given expression",
    ignoreJsonView = true)
    public ResponseEntity<?> calculateExpressionValue(@PathVariable(name = "userId") String userId,
                                                      @RequestBody Expression expression) {
        try {
            double value = service.evaluateExpression(userId, expression);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(Optional.of(value));
        } catch (InvalidExpressionException e) {
            Logger logger = Logger.getLogger(ExpressionEvaluatorController.class.getName());
            logger.log(Level.INFO, e, e::getMessage);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Optional.of(e.getMessage()));
        }
    }

    @GetMapping("/mostUsedOperand/{userId}")
    @Operation(summary = "Returns most used operand for the given user")
    public ResponseEntity<?> getMostUsedOperand(@PathVariable(name = "userId") String userId) {
        char value = service.getMostUserOperand(userId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(Optional.of(value));

    }
}
