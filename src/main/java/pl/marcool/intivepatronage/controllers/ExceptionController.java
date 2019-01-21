package pl.marcool.intivepatronage.controllers;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import pl.marcool.intivepatronage.models.ApiError;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class ExceptionController extends ResponseEntityExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    ResponseEntity ExceptionHandler(IllegalArgumentException ex) {
        String message = ex.getMessage();
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, message);
        if (message.contains("found")) apiError.setStatus(HttpStatus.NOT_FOUND);
        if (message.contains("already")) apiError.setStatus(HttpStatus.CONFLICT);
        return ResponseEntity.status(apiError.getStatus()).body(apiError);
    }

//    @ExceptionHandler({ Exception.class })
//    public ResponseEntity<Object> handleAll(Exception ex, WebRequest request) {
//        ApiError apiError = new ApiError(
//                HttpStatus.INTERNAL_SERVER_ERROR, "error occurred");
//        return new ResponseEntity<Object>(
//                apiError, new HttpHeaders(), apiError.getStatus());
//    }
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request) {
        List<String> errors = new ArrayList<String>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.add(error.getField() + ": " + error.getDefaultMessage());
        }
        for (ObjectError error : ex.getBindingResult().getGlobalErrors()) {
            errors.add(error.getObjectName() + ": " + error.getDefaultMessage());
        }
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, errors);
        return handleExceptionInternal(ex, apiError, headers, apiError.getStatus(), request);
    }

}

