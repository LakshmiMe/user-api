package com.jitpay.users.userapi.exception;

import com.jitpay.users.userapi.dto.ErrorDetailDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<Object> handleApplicationException(final ApplicationException ex) {
        return new ResponseEntity<>(ex.getErrorDetailDTO(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<Object> handleResponseStatusException(final ResponseStatusException ex) {
        return new ResponseEntity<>(ErrorDetailDTO.builder().issueCode(ex.getStatus().name())
                .errorMessage(ex.getReason()).build(), ex.getStatus());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex){
        List<String> errorList = new ArrayList<>();
        ex.getBindingResult().getAllErrors().forEach(error ->{
            String fieldName = ((FieldError) error).getField();
            String message = error.getDefaultMessage();
            errorList.add(fieldName+" "+message);
        });
        ErrorDetailDTO errorDetailDTO = ErrorDetailDTO.builder().issueCode(HttpStatus.BAD_REQUEST.name())
                .errorMessage(errorList.toString()).build();
        return new ResponseEntity<>(errorDetailDTO, HttpStatus.BAD_REQUEST);
    }
}
