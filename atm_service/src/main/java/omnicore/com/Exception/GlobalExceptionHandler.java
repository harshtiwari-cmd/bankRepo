package omnicore.com.Exception;

import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ConfigDataResourceNotFoundException.class)
    public ResponseEntity<String> handelNotFound(ConfigDataResourceNotFoundException ex)
    {
        return  new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }




}
