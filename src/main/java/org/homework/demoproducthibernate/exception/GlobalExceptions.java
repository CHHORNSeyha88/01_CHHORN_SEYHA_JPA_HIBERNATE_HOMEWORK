package org.homework.demoproducthibernate.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.*;

@RestControllerAdvice
public class GlobalExceptions {

    //-- Handle custom "Not Found" exceptions
    @ExceptionHandler(NotFoundException.class)
    public ProblemDetail handleNotFoundException(NotFoundException ex, HttpServletRequest request) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
        problemDetail.setTitle("Resource Not Found");
        problemDetail.setType(URI.create("/errors/not-found"));
        return problemDetail;
    }

    //-- Handle validation errors for @RequestBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail handleBodyValidationException(MethodArgumentNotValidException ex, HttpServletRequest request) {
        Map<String, Map<String, Object>> errorMap = new LinkedHashMap<>();

        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            String field = fieldError.getField();
            if (!errorMap.containsKey(field)) {
                Map<String, Object> error = new HashMap<>();
                error.put("field", field);
                error.put("rejectedValue", fieldError.getRejectedValue());
                error.put("message", fieldError.getDefaultMessage());
                errorMap.put(field, error);
            }
        }

        List<Map<String, Object>> errors = new ArrayList<>(errorMap.values());


        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problemDetail.setTitle("Validation Failed (Request Body)");
        problemDetail.setType(URI.create("/errors/validation/body"));
        problemDetail.setDetail("Some fields failed validation");
        problemDetail.setProperty("errors", errors);
        addStandardMetadata(problemDetail, request);
        return problemDetail;
    }

    //-- Handle validation errors for @RequestParam, @PathVariable
    @ExceptionHandler(HandlerMethodValidationException.class)
    public ProblemDetail handleMethodValidationException(HandlerMethodValidationException ex, HttpServletRequest request) {
        List<Map<String, String>> errors = new ArrayList<>();

        for (MessageSourceResolvable error : ex.getAllErrors()) {
            Map<String, String> errorDetails = new HashMap<>();
            errorDetails.put("message", Objects.requireNonNullElse(error.getDefaultMessage(), "Validation error"));
            errorDetails.put("code", Arrays.toString(error.getCodes()));
            errors.add(errorDetails);
        }

        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problemDetail.setTitle("Validation Failed (Request Param/Path)");
        problemDetail.setType(URI.create("/errors/validation/method"));
        problemDetail.setDetail("Method parameter validation failed");
        problemDetail.setProperty("errors", errors);
        addStandardMetadata(problemDetail, request);
        return problemDetail;
    }

    //-- Handle unexpected errors (optional fallback)
    @ExceptionHandler(Exception.class)
    public ProblemDetail handleUnexpectedException(Exception ex, HttpServletRequest request) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        problemDetail.setTitle("Internal Server Error");
        problemDetail.setType(URI.create("/errors/internal-server"));
        problemDetail.setDetail(ex.getMessage());
        addStandardMetadata(problemDetail, request);
        return problemDetail;
    }

    //-- Add standard fields to every response
    private void addStandardMetadata(ProblemDetail problemDetail, HttpServletRequest request) {
        problemDetail.setProperty("timestamp", LocalDateTime.now());
        problemDetail.setProperty("path", request.getRequestURI());
    }
}
