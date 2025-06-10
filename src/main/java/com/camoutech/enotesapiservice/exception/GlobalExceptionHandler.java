package com.camoutech.enotesapiservice.exception;

import com.camoutech.enotesapiservice.util.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler{

  @ExceptionHandler(NullPointerException.class)
  public ResponseEntity<?> handleNullPointerException(Exception e) {
    return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<?> handleMethodArgumentNotValidexception(MethodArgumentNotValidException e) {
    List<ObjectError> allErrors = e.getBindingResult().getAllErrors();
    Map<String, Object> error = new LinkedHashMap<>();
    allErrors.forEach(er-> {
      String msg = er.getDefaultMessage();
      String field = ((FieldError) (er)).getField();
      error.put(field, msg);
    });
    return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(ResourceNotFoundException.class)
  public ResponseEntity<?> handleResourceNotFoundException(Exception e) {
    return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(ValidationException.class)
  public ResponseEntity<?> handleValidationException(ValidationException e) {
    return new ResponseEntity<>(e.getErrors(), HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(ExistDataException.class)
  public ResponseEntity<?> handleExistDataException(ExistDataException e) {
    return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
  }

  @ExceptionHandler(HttpMessageNotReadableException.class)
  public ResponseEntity<?> HttpMessageNotReadableException(HttpMessageNotReadableException e) {
    return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
  }
}
