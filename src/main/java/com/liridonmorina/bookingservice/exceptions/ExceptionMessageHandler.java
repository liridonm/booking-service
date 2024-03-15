package com.liridonmorina.bookingservice.exceptions;

import com.liridonmorina.bookingservice.domain.dto.ResponseWrapper;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.HandlerMethod;

@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
@Slf4j
public class ExceptionMessageHandler {

    @ExceptionHandler({Exception.class, RuntimeException.class, Throwable.class, BookingServiceException.class})
    public ResponseEntity<ResponseWrapper> genericException(Throwable e, HandlerMethod handlerMethod) {
        return new ResponseEntity<>(ResponseWrapper.builder().success(false).message("Action failed: An error occurred!").build(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ResponseWrapper> notFoundException(NotFoundException e) {
        String message = ObjectUtils.isEmpty(e.getMessage()) ? e.getLocalizedMessage() : e.getMessage();
        log.error("Object not found because " + message, e);
        return new ResponseEntity<>(ResponseWrapper.builder().success(false).status(HttpStatus.NOT_FOUND.value()).message(message).build(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({ValidationException.class, ConstraintViolationException.class})
    public ResponseEntity<ResponseWrapper> validationException(ValidationException e) {
        String message = ObjectUtils.isEmpty(e.getMessage()) ? e.getLocalizedMessage() : e.getMessage();
        log.error("Data is not valid because " + message, e);
        return new ResponseEntity<>(ResponseWrapper.builder().success(false).status(HttpStatus.BAD_REQUEST.value()).message(message).build(), HttpStatus.BAD_REQUEST);
    }
}
