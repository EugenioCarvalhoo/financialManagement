package com.carvalho.solution.filter;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.carvalho.solution.exception.AppBusinessException;
import com.carvalho.solution.util.ResponseError;

@ControllerAdvice
public class GoblalExceptionHandler {
    
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ResponseError> handleGenericException(Exception ex) {
        return new ResponseEntity<>(ResponseError.defaultError("Ocorreu um erro no processo"), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(AppBusinessException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ResponseError> handleAppBusinessException(Exception ex) {
        return new ResponseEntity<>(ResponseError.bussinessError(ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
}
