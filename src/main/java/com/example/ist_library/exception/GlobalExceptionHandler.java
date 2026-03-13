package com.example.ist_library.exception;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler{

    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        Map<String, Object> map = new HashMap<>();

        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());

        map.put("code", HttpStatus.BAD_REQUEST.value());
        map.put("message", errors);
        map.put("timestamp", LocalDateTime.now());
        map.put("data", "");

        return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CustomValidationException.class)
    protected ResponseEntity<Object> ValidationExceptionCustomHandler(CustomValidationException exception) {
        Map<String, Object> map = new HashMap<>();

        map.put("code", HttpStatus.BAD_REQUEST.value());
        map.put("message", exception.getMessage());
        map.put("timestamp", LocalDateTime.now());
        map.put("data", "");

        return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<Object> GeneralExceptionHandler (Exception exception) {
        Map<String, Object> map = new HashMap<>();

        map.put("code", HttpStatus.INTERNAL_SERVER_ERROR.value());
        map.put("message", exception.getMessage());
        map.put("timestamp", LocalDateTime.now());
        map.put("data", "");

        return new ResponseEntity<>(map, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
