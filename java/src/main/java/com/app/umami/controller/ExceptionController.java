package com.app.umami.controller;

import com.app.umami.exception.AlreadyExistException;
import com.app.umami.exception.ForbiddenException;
import com.app.umami.exception.InternalServerException;
import com.app.umami.exception.ResourceNotFoundException;
import com.app.umami.pojo.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class ExceptionController {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Response> handleIllegalArgumentException(IllegalArgumentException exception) {
        Response error = new Response(HttpStatus.BAD_REQUEST.value(), exception.getMessage());
        return new ResponseEntity(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Response> handleResourceNotFoundException(ResourceNotFoundException exception) {
        Response error = new Response(HttpStatus.NOT_FOUND.value(), exception.getMessage());
        return new ResponseEntity(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AlreadyExistException.class)
    public ResponseEntity<Response> handleAlreadyExistException(AlreadyExistException exception) {
        Response error = new Response(HttpStatus.CONFLICT.value(), exception.getMessage());
        return new ResponseEntity(error, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<Response> handleAlreadyForbiddenException(ForbiddenException exception) {
        Response error = new Response(HttpStatus.FORBIDDEN.value(), exception.getMessage());
        return new ResponseEntity(error, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(InternalServerException.class)
    public ResponseEntity<Response> handleInternalServerException(InternalServerException exception) {
        Response error = new Response(HttpStatus.INTERNAL_SERVER_ERROR.value(), exception.getMessage());
        return new ResponseEntity(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Response> handleConstraintViolationException(MethodArgumentNotValidException exception) {
        List<String> details = new ArrayList<>();
        for(ObjectError error : exception.getBindingResult().getAllErrors()) {
            details.add(error.getDefaultMessage());
        }

        Response error = new Response(HttpStatus.BAD_REQUEST.value(), details.get(0));
        return new ResponseEntity(error, HttpStatus.BAD_REQUEST);
    }
}
