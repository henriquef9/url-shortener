package com.henrique.urlshortener.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class RestExceptionHandler {


    @ExceptionHandler(NotFoundEntityException.class)
    public ResponseEntity<HttpError> handleNotFoundEntityException(NotFoundEntityException e) {

        HttpError error = new HttpError(
                HttpStatus.NOT_FOUND.value(),
                e.getMessage(),
                LocalDateTime.now()
        );

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);

    }

    @ExceptionHandler(ShortUrlExpiredException.class)
    public ResponseEntity<HttpError> handleShortUrlExpiredException(ShortUrlExpiredException e) {
        HttpError error = new HttpError(
                HttpStatus.FORBIDDEN.value(),
                e.getMessage(),
                LocalDateTime.now()
        );

        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(error);
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<HttpError> handleGenericException(Exception ex) {
        HttpError error = new HttpError(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Internal Error: " + ex.getMessage(),
                LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }

}
