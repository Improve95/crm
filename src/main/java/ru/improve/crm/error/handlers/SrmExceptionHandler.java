package ru.improve.crm.error.handlers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.improve.crm.error.exceptions.AlreadyExistException;
import ru.improve.crm.error.exceptions.CustomCrmException;
import ru.improve.crm.error.exceptions.InDtoException;
import ru.improve.crm.error.exceptions.NotFoundException;
import ru.improve.crm.error.responseBody.CustomErrorResponse;
import ru.improve.crm.error.responseBody.DefaultErrorResponse;

@RestControllerAdvice
public class SrmExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<DefaultErrorResponse> handleAllException(Exception ex) {
        DefaultErrorResponse defaultErrorResponse = new DefaultErrorResponse(ex.getMessage());
        return new ResponseEntity<>(defaultErrorResponse, determineHttpStatus(ex));
    }

    @ExceptionHandler
    public ResponseEntity<CustomErrorResponse> handleMyExceptions(CustomCrmException ex) {
        CustomErrorResponse customErrorResponse = new CustomErrorResponse(
                ex.getMessage(), ex.getFieldsWithError()
        );
        return new ResponseEntity<>(customErrorResponse, determineHttpStatus(ex));
    }

    private HttpStatus determineHttpStatus(Exception ex) {
        if (ex instanceof InDtoException ||
            ex instanceof AlreadyExistException) {

            return HttpStatus.BAD_REQUEST;
        }

        if (ex instanceof NotFoundException) {
            return HttpStatus.NOT_FOUND;
        }

        return HttpStatus.INTERNAL_SERVER_ERROR;
    }

}
