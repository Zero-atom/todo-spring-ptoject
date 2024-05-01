package com.example.todospringptoject.exception.handler;

import com.example.todospringptoject.exception.UserAlreadyExistException;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class ResponseEntityExceptionHandler {

    //если нужно перехватить ответ и отпрвить токо код например без остальных полей
//    @ExceptionHandler(UserAlreadyExistException.class)
//    public ResponseEntity<String> handleUserAlreadyExistException(UserAlreadyExistException ex) {
//        // модификация ответа с полем "USER_ALREADY_EXIST"
//        return ResponseEntity.status(HttpStatus.CONFLICT).body("USER_ALREADY_EXIST");
//    }

    @ExceptionHandler(UserAlreadyExistException.class)
    public ResponseEntity<Object> handleUserAlreadyExistException(UserAlreadyExistException ex) {
        // Создание объекта ответа с дополнительным полем "generalCode"
        ExceptionBody exceptionBody = new ExceptionBody();
        exceptionBody.setTimestamp(LocalDateTime.now().toString());
        exceptionBody.setHttpCode(HttpStatus.CONFLICT.value());
        exceptionBody.setGeneralCode("USER_ALREADY_EXIST");
        exceptionBody.setMessage(ex.getMessage());
        exceptionBody.setError(HttpStatus.CONFLICT.getReasonPhrase());

        return ResponseEntity.badRequest().body(exceptionBody);
    }

    // Вспомогательный класс для представления тела ответа
    @Getter
    @Setter
    private static class ExceptionBody {
        private String generalCode;
        private String message;
        private String timestamp;
        private int httpCode;

        private String error;
    }
}

