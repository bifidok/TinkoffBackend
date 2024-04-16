package edu.java.bot.controllers.handlers;

import edu.java.bot.dto.ApiErrorResponse;
import edu.java.bot.exceptions.LinkUpdateNotCorrectException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class ExceptionApiHandler {

    @ExceptionHandler(LinkUpdateNotCorrectException.class)
    public ResponseEntity<ApiErrorResponse> notFoundException(LinkUpdateNotCorrectException exception) {
        log.warn(exception.getMessage());
        ApiErrorResponse apiErrorResponse = new ApiErrorResponse(
            "Link not found",
            HttpStatus.BAD_REQUEST.toString(),
            exception.getClass().getName(),
            exception.getMessage()
        );
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(apiErrorResponse);
    }
}
