package io.venly.words;

import io.venly.words.dto.ErrorResponse;
import io.venly.words.exception.PathNotFoundException;
import io.venly.words.exception.RelationAlreadyExistsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.Instant;
import java.util.List;


@Component
@ControllerAdvice
public class GlobalErrorHandler extends ResponseEntityExceptionHandler {
    private final Logger log = LoggerFactory.getLogger(GlobalErrorHandler.class);

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        log.error("Handling illegal argument error", ex);
        var message = "";
        List<FieldError> errors = ex.getBindingResult().getFieldErrors();
        if (!errors.isEmpty()) {
            message = errors.get(0).getDefaultMessage();
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(Instant.now(), HttpStatus.BAD_REQUEST, message));
    }

    @ExceptionHandler(RelationAlreadyExistsException.class)
    public Object handleRelationAlreadyExistsException(RelationAlreadyExistsException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(Instant.now(), HttpStatus.BAD_REQUEST, exception.getMessage()));
    }

    @ExceptionHandler(PathNotFoundException.class)
    public Object handlePathNotFoundException(PathNotFoundException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(Instant.now(), HttpStatus.BAD_REQUEST, exception.getMessage()));
    }

    @ExceptionHandler
    ResponseEntity<ErrorResponse> handleUnknownError(Exception exception) {
        log.error("Handling internal server error", exception);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponse(Instant.now(), HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage()));
    }


}