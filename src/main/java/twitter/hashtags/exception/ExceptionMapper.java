package twitter.hashtags.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@ControllerAdvice
public class ExceptionMapper {

    @ExceptionHandler({HttpConnectionException.class})
    public ResponseEntity<String> handleHttpConnectionException(HttpConnectionException e) {
        return error(INTERNAL_SERVER_ERROR, e);
    }

    @ExceptionHandler({ValidationException.class})
    public ResponseEntity<String> handleValidationException(ValidationException e) {
        return error(BAD_REQUEST, e);
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<String> handleException(Exception e){
        return error(INTERNAL_SERVER_ERROR, e);
    }

    private ResponseEntity<String> error(HttpStatus status, Exception e) {
        return ResponseEntity.status(status).body(e.getMessage());
    }
}
