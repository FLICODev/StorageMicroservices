package it.dcm.storage.exception;

import it.dcm.rest.exception.ModelResponseEntityException;
import it.dcm.rest.exception.ResponseEntityException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class RestExceptionHandler {


    @ExceptionHandler(ResponseEntityException.class)
    public ResponseEntity<ModelResponseEntityException> handleResponseEntityException(final ResponseEntityException exception){
        final ModelResponseEntityException entityException = new ModelResponseEntityException();
        entityException.setCode(exception.getExceptionType().getError());
        entityException.setMessage(exception.getMessage());
        entityException.setStatus(exception.getHttpStatus().toString());
        return new ResponseEntity<>(entityException, exception.getHttpStatus());
    }
	
}
