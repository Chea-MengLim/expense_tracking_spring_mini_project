package org.example.kps_group_01_spring_mini_project.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

import java.time.LocalDateTime;
import java.util.HashMap;

@ControllerAdvice
public class GlobalException {
    @ExceptionHandler(NotFoundException.class)
    public ProblemDetail handleNotFoundException(NotFoundException ex){
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
                HttpStatus.NOT_FOUND,
                ex.getMessage()
        );
        problemDetail.setTitle("Not found");
        problemDetail.setProperty("timestamp" , LocalDateTime.now());
        return problemDetail;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail MethodArgumentNotValidException(MethodArgumentNotValidException ex){
        HashMap<String , String> error = new HashMap<>();
        for(FieldError fieldError : ex.getBindingResult().getFieldErrors()){
            error.put(fieldError.getField(), fieldError.getDefaultMessage());
        }
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
                HttpStatus.BAD_REQUEST,
                "Bad request"
        );
        problemDetail.setTitle("Bad request");
        problemDetail.setProperty("timestamp" , LocalDateTime.now());
        problemDetail.setProperty("errors", error);
        return problemDetail;
    }
    @ExceptionHandler(HandlerMethodValidationException.class)
    public ProblemDetail handleMethodValidationException(HandlerMethodValidationException e){
        HashMap<String, String> errors = new HashMap<>();

        for (var parameterError : e.getAllValidationResults()){
            String parameterName = parameterError.getMethodParameter().getParameterName();
            for(var error : parameterError.getResolvableErrors()){
                errors.put(parameterName,error.getDefaultMessage());
            }
        }
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
                HttpStatus.BAD_REQUEST,
                "Bad request"
        );
        problemDetail.setTitle("Bad Request");
        problemDetail.setProperty("timestamp", LocalDateTime.now());
        problemDetail.setProperty("error", errors);
        return problemDetail;
    }

}

