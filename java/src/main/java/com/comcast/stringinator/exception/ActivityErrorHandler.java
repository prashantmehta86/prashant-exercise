package com.comcast.stringinator.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.validation.ConstraintViolationException;

@ControllerAdvice
public class ActivityErrorHandler {

    private static final Logger logger = LoggerFactory.getLogger(ActivityErrorHandler.class);

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<String> handleConstraintViolationException(final ConstraintViolationException exception) {
        logger.info("Constraint violation raised: {} for API call, failed with exception: {}", exception.getConstraintViolations(), exception.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Input string length should be greater than 0.");
    }
}
