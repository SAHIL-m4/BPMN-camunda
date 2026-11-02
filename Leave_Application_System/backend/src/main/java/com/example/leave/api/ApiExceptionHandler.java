package com.example.leave.api;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
@RestControllerAdvice
public class ApiExceptionHandler {
 @ExceptionHandler(IllegalArgumentException.class)
 @ResponseStatus(HttpStatus.BAD_REQUEST)
 public Map<String,String> badRequest(Exception e) { return Map.of("message",e.getMessage()); }
 @ExceptionHandler(IllegalStateException.class)
 @ResponseStatus(HttpStatus.CONFLICT)
 public Map<String,String> conflict(Exception e) { return Map.of("message",e.getMessage()); }
}
