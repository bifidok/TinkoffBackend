package edu.java.scrapper;

import edu.java.scrapper.dto.ApiErrorResponse;
import edu.java.scrapper.exceptions.ChatNotCreatedException;
import edu.java.scrapper.exceptions.ChatNotFoundException;
import edu.java.scrapper.exceptions.LinkNotCorrectException;
import edu.java.scrapper.exceptions.LinkNotCreatedException;
import edu.java.scrapper.exceptions.LinkNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class ExceptionApiHandler {

    @ExceptionHandler(NumberFormatException.class)
    public ResponseEntity<ApiErrorResponse> notValidNumberException(NumberFormatException exception) {
        log.warn(exception.getMessage());
        ApiErrorResponse apiErrorResponse = new ApiErrorResponse(
            "Not correct id",
            HttpStatus.BAD_REQUEST.toString(),
            exception.getClass().getName(),
            exception.getMessage(),
            exception.getStackTrace()
        );
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(apiErrorResponse);
    }

    @ExceptionHandler(ChatNotCreatedException.class)
    public ResponseEntity<ApiErrorResponse> chatNotCreatedException(ChatNotCreatedException exception) {
        log.warn(exception.getMessage());
        ApiErrorResponse apiErrorResponse = new ApiErrorResponse(
            "Some problems with chat creation",
            HttpStatus.BAD_REQUEST.toString(),
            exception.getClass().getName(),
            exception.getMessage(),
            exception.getStackTrace()
        );
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(apiErrorResponse);
    }

    @ExceptionHandler(ChatNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> chatNotFoundException(ChatNotFoundException exception) {
        log.warn(exception.getMessage());
        ApiErrorResponse apiErrorResponse = new ApiErrorResponse(
            "Chat not found",
            HttpStatus.NOT_FOUND.toString(),
            exception.getClass().getName(),
            exception.getMessage(),
            exception.getStackTrace()
        );
        return ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body(apiErrorResponse);
    }

    @ExceptionHandler(LinkNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> linkNotFoundException(LinkNotFoundException exception) {
        log.warn(exception.getMessage());
        ApiErrorResponse apiErrorResponse = new ApiErrorResponse(
            "link not found",
            HttpStatus.NOT_FOUND.toString(),
            exception.getClass().getName(),
            exception.getMessage(),
            exception.getStackTrace()
        );
        return ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body(apiErrorResponse);
    }

    @ExceptionHandler(LinkNotCorrectException.class)
    public ResponseEntity<ApiErrorResponse> linkNotCorrectException(LinkNotCorrectException exception) {
        log.warn(exception.getMessage());
        ApiErrorResponse apiErrorResponse = new ApiErrorResponse(
            "link not correct",
            HttpStatus.BAD_REQUEST.toString(),
            exception.getClass().getName(),
            exception.getMessage(),
            exception.getStackTrace()
        );
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(apiErrorResponse);
    }

    @ExceptionHandler(LinkNotCreatedException.class)
    public ResponseEntity<ApiErrorResponse> linkDoubleCreationException(LinkNotCreatedException exception) {
        log.warn(exception.getMessage());
        ApiErrorResponse apiErrorResponse = new ApiErrorResponse(
            "link already exist",
            HttpStatus.BAD_REQUEST.toString(),
            exception.getClass().getName(),
            exception.getMessage(),
            exception.getStackTrace()
        );
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(apiErrorResponse);
    }
}
