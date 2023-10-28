package org.heapminds.exception;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@RestControllerAdvice
public class ControllerAdvisor extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {HMException.class})
    public ResponseEntity handleUnauthorizedException(
            HMException ex, WebRequest request) {
                Map hmException = new HashMap();
                hmException.put("success",false);
                hmException.put("error",ex.getMessage());
                hmException.put("time-stamp",new Date().getTime());
                return new ResponseEntity(hmException, HttpStatus.BAD_REQUEST);
    }

    @Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        
		Map<String, String> invalidFields = new HashMap<>();
		ex.getBindingResult().getAllErrors().forEach((error) ->{
			
			String fieldName = ((FieldError) error).getField();
			String message = error.getDefaultMessage();
			invalidFields.put(fieldName, message);

		});

        StringBuilder errorMessages = new StringBuilder();
invalidFields.values().forEach((val)->{
    if (errorMessages.length() > 0) {
        errorMessages.append(", ");
    }
    errorMessages.append(val);
});


        Map <String, Object> errors  = new HashMap<>();
            errors.put("error", errorMessages);
            errors.put("success", false);
		return new ResponseEntity<Object>(errors, HttpStatus.BAD_REQUEST);
	}
    
}
