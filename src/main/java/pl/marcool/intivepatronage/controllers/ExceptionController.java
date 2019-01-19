package pl.marcool.intivepatronage.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import pl.marcool.intivepatronage.services.MyExceptions;


@RestControllerAdvice
public class ExceptionController {

    @ExceptionHandler(MyExceptions.class)
    ResponseEntity ExceptionHandler(MyExceptions ex) {
        return ResponseEntity.status(ex.errorCode).body(ex.getMessage());
    }

}

