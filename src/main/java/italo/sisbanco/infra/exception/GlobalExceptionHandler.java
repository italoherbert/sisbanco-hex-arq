package italo.sisbanco.infra.exception;

import org.springframework.context.NoSuchMessageException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import italo.sisbanco.core.exception.ErrorException;
import italo.sisbanco.infra.entrypoint.dto.ErrorResponse;

@ControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(ErrorException.class)
    public ResponseEntity<ErrorResponse> throwErrorResponse( ErrorException e ) {
        try {            
            return ResponseEntity.badRequest().body( new ErrorResponse( e.error() ) );
        } catch ( NoSuchMessageException e2 ) {
            return ResponseEntity.badRequest().body( new ErrorResponse( e2.getMessage() ) );
        }
    }

}
