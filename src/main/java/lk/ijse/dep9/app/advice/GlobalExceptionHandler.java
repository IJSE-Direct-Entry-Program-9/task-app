package lk.ijse.dep9.app.advice;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.*;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, Object> validationExceptions(MethodArgumentNotValidException exp){
        Map<String, Object> errAttributes = new LinkedHashMap<>();
        errAttributes.put("status", HttpStatus.BAD_REQUEST.value());
        errAttributes.put("error", HttpStatus.BAD_REQUEST.getReasonPhrase());
        errAttributes.put("timestamp", new Date().toString());
        List<String> validationErrList = exp.getFieldErrors().stream()
                .map(err -> err.getField() + ": " + err.getDefaultMessage())
                .collect(Collectors.toList());
        errAttributes.put("errors", validationErrList);
        return errAttributes;
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(DuplicateKeyException.class)
    public Map<String, Object> duplicateEntityExceptionHandler(){
        Map<String, Object> errAttributes = new LinkedHashMap<>();
        errAttributes.put("status", HttpStatus.CONFLICT.value());
        errAttributes.put("error", HttpStatus.CONFLICT.getReasonPhrase());
        errAttributes.put("message", "Duplicate entity found");
        errAttributes.put("timestamp", new Date().toString());
        return errAttributes;
    }
}
