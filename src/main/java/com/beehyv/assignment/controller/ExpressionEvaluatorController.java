package com.beehyv.assignment.controller;

import com.beehyv.assignment.dto.ExpressionValueResponseDto;
import com.beehyv.assignment.dto.MostUsedOperandDto;
import com.beehyv.assignment.exceptions.InvalidExpressionException;
import com.beehyv.assignment.model.Expression;
import com.beehyv.assignment.service.ExpressionEvaluatorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@Tag(name = "Expression evaluator")
public class ExpressionEvaluatorController {

    private final ExpressionEvaluatorService service;

    public ExpressionEvaluatorController(ExpressionEvaluatorService service) {
        this.service = service;
    }


    @PostMapping(path = "/evaluate/{userId}", consumes = "application/json")
    @Operation(summary = "Returns the calculated value for the given expression")
    @Parameter(description = "ID of the user using this api", example = "sanket", name = "userId")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ExpressionValueResponseDto.class))),
            @ApiResponse(responseCode = "400",
                    content = @Content(schema = @Schema(implementation = String.class, name = "message"),
                            examples = {@ExampleObject(value = "Given expression is invalid, unable evaluate")})
            )})
    public ResponseEntity<?> calculateExpressionValue(@PathVariable(name = "userId") String userId,
                                                      @RequestBody Expression expression) {
        try {
            double value = service.evaluateExpression(userId, expression);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(Optional.of(new ExpressionValueResponseDto(value)));
        } catch (InvalidExpressionException e) {
            Logger logger = Logger.getLogger(ExpressionEvaluatorController.class.getName());
            logger.log(Level.INFO, e, e::getMessage);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Optional.of(e.getMessage()));
        }
    }

    @GetMapping(path = "/mostUsedOperand/{userId}")
    @Operation(summary = "Returns most used operand for the given user")
    @Parameter(name = "userId", description = "ID of the user using this api", example = "sanket")
    @ApiResponse(responseCode = "200",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = MostUsedOperandDto.class)))
    public MostUsedOperandDto getMostUsedOperand(@PathVariable(name = "userId") String userId) {
        char value = service.getMostUserOperand(userId);
        return new MostUsedOperandDto(value);

    }
}
