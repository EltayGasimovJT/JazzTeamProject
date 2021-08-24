package org.jazzteam.eltay.gasimov.controller.exception;

import lombok.extern.java.Log;
import org.jazzteam.eltay.gasimov.controller.exception.model.CustomResponseError;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.sql.SQLException;

@ControllerAdvice
@Log
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<CustomResponseError> handleIllegalArgumentException(IllegalArgumentException e) {
        log.severe(e.getMessage());
        CustomResponseError response = new CustomResponseError(HttpStatus.CONFLICT, e.getMessage(), e);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(SQLException.class)
    public ResponseEntity<CustomResponseError> handleSQLException(SQLException e) {
        log.severe(e.getMessage());
        CustomResponseError response = new CustomResponseError(HttpStatus.CONFLICT, e.getMessage(), e);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        CustomResponseError apiError = new CustomResponseError(HttpStatus.BAD_REQUEST, ex.getMessage(), ex);
        return new ResponseEntity<>(apiError, status);
    }
}
