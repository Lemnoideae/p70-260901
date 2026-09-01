package com.back.p67260811.global.exceptionHandler;

import com.back.p67260811.global.dto.RsData;
import com.back.p67260811.global.exception.ServiceException;
import org.springframework.http.converter.HttpMessageNotReadableException;
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
        return new RsData<Void>(
                "404-1",
                "존재하지 않는 데이터입니다."
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public RsData<Void> methodArgumentNotValidException(MethodArgumentNotValidException e){

        String message = e.getBindingResult()
                .getAllErrors()
                .stream()
                .filter(error -> error instanceof FieldError)
                .map(error -> (FieldError) error)
                .map(error -> error.getField() + "-" + error.getCode() + "-" + error.getDefaultMessage())
                .sorted(Comparator.comparing(String::toString))
                .collect(Collectors.joining("\n"));

        return new RsData<Void>(
                "400-1",
                message
        );
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public RsData<Void> handleException(HttpMessageNotReadableException e) {
        return new RsData<Void>(
                "400-2",
                "잘못된 형식의 요청 데이터입니다."
        );
    }

    @ExceptionHandler(ServiceException.class)
    public RsData<Void> handleServiceException(ServiceException e) {
        return new RsData<Void>(e.getResultCode(), e.getMsg());
    }
}