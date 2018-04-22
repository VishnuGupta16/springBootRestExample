package org.vg.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.vg.exception.runtime.ResourceNotFoundException;

@ControllerAdvice(annotations=RestController.class)
@RestController
public class CustomizeExceptionHandler  extends ResponseEntityExceptionHandler{
	
	@ExceptionHandler(Exception.class)
	public final ResponseEntity<Object> handleAllException(Exception ex, WebRequest request) {
		ExceptionResponse response = new ExceptionResponse(ex.getMessage(), 
				request.getDescription(false), LocalDateTime.now());
		return new ResponseEntity<Object>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	

	@ExceptionHandler(ResourceNotFoundException.class)
	public final ResponseEntity<Object> handleResourceNotFoundExceptionException(Exception ex, WebRequest request) {
		ExceptionResponse response = new ExceptionResponse(ex.getMessage(), 
				request.getDescription(false), LocalDateTime.now());
		return new ResponseEntity<Object>(response, HttpStatus.NOT_FOUND);
	}
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		ExceptionResponse response = new ExceptionResponse("Validation Error", 
				ex.getBindingResult().toString(), LocalDateTime.now());
		return new ResponseEntity<Object>(response, HttpStatus.BAD_REQUEST);
	}
	
}