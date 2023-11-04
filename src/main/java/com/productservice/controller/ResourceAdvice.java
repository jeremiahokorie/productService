package com.productservice.controller;


import com.productservice.core.exceptions.ErrorDetail;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

/**
 * Project title: authservice
 *
 * @author jeremiah Imo
 * Date: 9/21/23
 * Time: 9:02 PM
 */
@RestControllerAdvice
public class ResourceAdvice {

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorDetail> handleBadCredentialsException(BadCredentialsException ex, WebRequest request) {
        ErrorDetail errorDetails = ErrorDetail.builder()
                .message(ex.getMessage())
                .code(HttpStatus.UNAUTHORIZED.value())
                .details(request.getDescription(true))
                .timestamp(new Date())
                .build();
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorDetails);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ErrorDetail> handleUsernameNotFoundException(UsernameNotFoundException ex, WebRequest request) {
        ErrorDetail errorDetails = ErrorDetail.builder()
                .message(ex.getMessage())
                .code(HttpStatus.UNAUTHORIZED.value())
                .details(request.getDescription(true))
                .timestamp(new Date())
                .build();
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorDetails);
    }
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorDetail> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex, WebRequest request) {
        ErrorDetail errorDetails = ErrorDetail.builder()
                .message(ex.getMessage())
                .code(HttpStatus.BAD_REQUEST.value())
                .details(request.getDescription(true))
                .timestamp(new Date())
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDetails);
    }
}
