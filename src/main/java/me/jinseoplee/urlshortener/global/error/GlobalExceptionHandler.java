package me.jinseoplee.urlshortener.global.error;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException e
    ) {
        List<ErrorResponse.FieldError> errors = extractFieldErrors(e.getBindingResult());
        ErrorCode errorCode = ErrorCode.INVALID_INPUT_VALUE;
        ErrorResponse response = ErrorResponse.of(errorCode, errors);

        return ResponseEntity
                .status(errorCode.getStatus())
                .body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception e) {
        log.error("Unexpected exception occurred", e);

        ErrorCode errorCode = ErrorCode.INTERNAL_SERVER_ERROR;
        ErrorResponse response = ErrorResponse.of(errorCode);

        return ResponseEntity
                .status(errorCode.getStatus())
                .body(response);
    }

    private List<ErrorResponse.FieldError> extractFieldErrors(BindingResult bindingResult) {
        return bindingResult.getFieldErrors().stream()
                .map(error -> new ErrorResponse.FieldError(
                        error.getField(),
                        error.getRejectedValue() == null ? "" : error.getRejectedValue().toString(),
                        error.getDefaultMessage()
                ))
                .toList();
    }
}
