package org.jazzteam.eltay.gasimov.controller.exception;

import lombok.extern.java.Log;
import org.jazzteam.eltay.gasimov.controller.exception.model.CustomResponseError;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@Log
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<CustomResponseError> handleIllegalArgumentException(Exception e) {
        CustomResponseError response = new CustomResponseError(HttpStatus.CONFLICT, e.getMessage(), e);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @Override
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        CustomResponseError apiError = new CustomResponseError(HttpStatus.BAD_REQUEST, ex.getMessage(), ex);
        return new ResponseEntity<>(apiError, status);
    }
}
