package io.metadata.school.controller;

import io.metadata.school.model.dto.response.ApiErrorResponse;
import io.metadata.school.model.exception.NotFoundException;
import io.metadata.school.model.exception.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;


@ControllerAdvice
class ControllerExceptionAdvice {

    @ResponseBody
    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    ResponseEntity<ApiErrorResponse> notFoundHandler(NotFoundException exception) {
        return ResponseEntity.ok(new ApiErrorResponse(exception.getMessage()));
    }

    @ResponseBody
    @ExceptionHandler(ValidationException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    ResponseEntity<ApiErrorResponse> validationExceptionHandler(ValidationException exception) {
        return ResponseEntity.ok(new ApiErrorResponse(exception.getMessage()));
    }
}