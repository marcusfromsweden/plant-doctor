package com.marcusfromsweden.plantdoctor.exception;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            if (error instanceof FieldError fieldError) {
                errors.put(fieldError.getField(), fieldError.getDefaultMessage());
            }
        });
        return errors;
    }

    //todo add more exceptions for conflict responses
    @ExceptionHandler({
            DuplicateGrowingLocationNameException.class,
            DuplicateBotanicalSpeciesNameException.class,
            MultipleSeedPackageFoundException.class})
    @ResponseStatus(HttpStatus.CONFLICT)
    public Map<String, String> handleDuplicateNameException(RuntimeException ex) {
        return Map.of("error", ex.getMessage());
    }

    //todo add exception handler for not found responses
    @ExceptionHandler({
            SeedPackageNotFoundByIdException.class,
            PlantNotFoundByIdException.class,
            BotanicalSpeciesNotFoundByIdException.class,
            GrowingLocationNotFoundByIdException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> handleNotFoundException(RuntimeException ex) {
        return Map.of("error", ex.getMessage());
    }
}
