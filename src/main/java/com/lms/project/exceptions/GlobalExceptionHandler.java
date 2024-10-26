package com.lms.project.exceptions;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import java.util.stream.Collectors;
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private static final String ERROR = "Oops!";
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatusCode status,
                                                                  WebRequest request) {
        ErrorMessage message = new ErrorMessage();
        String errorMessage = ex.getBindingResult().getFieldErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.joining(", "));
        message.setMessage(errorMessage);
        message.setErrorCode("1010");
        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> handleConstraintExceptions(ConstraintViolationException ex) {
        ErrorMessage message = new ErrorMessage();        // Extracting error details
        String errorMessage = ex.getConstraintViolations().stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.joining(", "));
        message.setMessage(errorMessage);
        message.setErrorCode("1010");
        return new ResponseEntity<>(message, HttpStatus.NOT_ACCEPTABLE);
    }
    @ExceptionHandler(value = ApplicationException.class)
    public ResponseEntity<ErrorMessage> handleBaseException(ApplicationException ex) {
        log.error(ERROR, ex);
        ErrorMessage errorMessage = new ErrorMessage();
        errorMessage.setErrorCode(ex.getErrorCode().getErrorCode());
        errorMessage.setMessage(ex.getMessage());
        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler({Exception.class})
    public ResponseEntity<ErrorMessage> handleRuntimeException(Exception ex) {
        log.error(ERROR, ex);
        ErrorHandle errorHandle = ErrorCode.CAP_1001;
        ErrorMessage errorMessage = new ErrorMessage();
        errorMessage.setErrorCode(errorHandle.getErrorCode());
        errorMessage.setMessage(ex.getMessage());
        return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}