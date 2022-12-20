package lk.ijse.dep9.app.advice;

import lk.ijse.dep9.app.dto.ErrorResponseMsgDTO;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Throwable.class)
    public ErrorResponseMsgDTO uncaughtExceptions(Throwable t){
        return new ErrorResponseMsgDTO(t.getMessage(), 405);
    }
}
