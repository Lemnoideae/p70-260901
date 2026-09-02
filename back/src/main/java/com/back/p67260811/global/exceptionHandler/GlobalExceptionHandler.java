package com.back.p67260811.global.exceptionHandler;

import com.back.p67260811.global.dto.RsData;
import com.back.p67260811.global.exception.ServiceException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Comparator;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NoSuchElementException.class)
    public RsData<Void> noSuchElementException(){
        return new RsData<>(
                "404-1",
                "존재하지 않는 데이터입니다."
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<RsData<Void>> methodArgumentNotValidException(MethodArgumentNotValidException e){

        String message = e.getBindingResult()
                .getAllErrors()
                .stream()
                .filter(error -> error instanceof FieldError)
                .map(error -> (FieldError) error)
                .map(error -> error.getField() + "-" + error.getCode() + "-" + error.getDefaultMessage())
                .sorted(Comparator.comparing(String::toString))
                .collect(Collectors.joining("\n"));

        return ResponseEntity.status(e.getStatusCode())
                .body(new RsData<>("400-1", message));
    }

    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<RsData<Void>> handleException(ServiceException e) {
        return ResponseEntity.status(e.getStatusCode()).body(
                new RsData<>(e.getResultCode(), e.getMsg()));
    }
}