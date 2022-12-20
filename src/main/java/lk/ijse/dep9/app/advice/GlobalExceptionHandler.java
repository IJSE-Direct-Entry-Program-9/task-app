package lk.ijse.dep9.app.advice;

import lk.ijse.dep9.app.dto.ErrorResponseMsgDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Throwable.class)
    public ErrorResponseMsgDTO uncaughtExceptions(Throwable t){
        t.printStackTrace();
        return new ErrorResponseMsgDTO(t.getMessage(), 405);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorResponseMsgDTO validationExceptions(MethodArgumentNotValidException e){
        String message = e.getFieldErrors().stream().
                map(err -> err.getField() + ": " + err.getDefaultMessage() + ", ")
                .reduce((prev, cur) -> prev + cur).get();
        return new ErrorResponseMsgDTO(message, 400);
    }
}
