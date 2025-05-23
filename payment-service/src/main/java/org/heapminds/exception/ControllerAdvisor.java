package org.heapminds.exception;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ControllerAdvisor extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = { PaymentException.class })
    public ResponseEntity<Object> handleUnauthorizedException(
            PaymentException ex, WebRequest request) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", new Date().getTime());
        body.put("message", ex.getMessage()); // Update the message to reflect the UnauthorizedException
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

}
