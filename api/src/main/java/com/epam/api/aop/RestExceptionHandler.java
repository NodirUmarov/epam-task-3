package com.epam.api.aop;

import com.epam.api.model.EndPointErrorResponse;
import com.epam.business.exception.EntityExistsException;
import com.epam.business.exception.EntityIdNotFoundException;
import com.epam.business.exception.EntityIsUsingException;
import com.epam.business.exception.EntityNameNotFoundException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@RequiredArgsConstructor
@Order(Ordered.HIGHEST_PRECEDENCE)
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    private final ResponseMessages responseMessages;

    @Override
    protected @NonNull ResponseEntity<Object> handleHttpMessageNotReadable(@NonNull HttpMessageNotReadableException ex,
                                                                           @NonNull HttpHeaders headers,
                                                                           @NonNull HttpStatus status,
                                                                           @NonNull WebRequest request) {
        return buildResponseEntity(generateApiError(responseMessages.invalidFormat, HttpStatus.BAD_REQUEST));
    }

    @ExceptionHandler(EntityNameNotFoundException.class)
    protected ResponseEntity<Object> handleEntityNotFound() {
        return buildResponseEntity(generateApiError(responseMessages.notFound, HttpStatus.NOT_FOUND));
    }

    @ExceptionHandler(EntityIsUsingException.class)
    protected ResponseEntity<Object> handleOperationDenied() {
        return buildResponseEntity(generateApiError(responseMessages.cannotDelete, HttpStatus.NOT_ACCEPTABLE));
    }

    @ExceptionHandler(EntityIdNotFoundException.class)
    protected ResponseEntity<Object> handleEntityIdNotFound() {
        return buildResponseEntity(generateApiError(responseMessages.notFound, HttpStatus.NOT_FOUND));
    }

    @ExceptionHandler(EntityExistsException.class)
    protected ResponseEntity<Object> handleEntityExists() {
        return buildResponseEntity(generateApiError(responseMessages.duplicateData, HttpStatus.NOT_ACCEPTABLE));
    }

    private ResponseEntity<Object> buildResponseEntity(EndPointErrorResponse apiErrorModel) {
        return new ResponseEntity<>(apiErrorModel, apiErrorModel.getStatus());
    }

    private EndPointErrorResponse generateApiError(String message, HttpStatus httpStatus) {
        return new EndPointErrorResponse(httpStatus, message);
    }
}