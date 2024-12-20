package com.stock_service.stock.infra.exception;

import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import com.stock_service.stock.domain.brand.exception.ex.BrandAlreadyExistException;
import com.stock_service.stock.domain.brand.exception.ex.BrandNotFoundByIdException;
import com.stock_service.stock.domain.brand.exception.ex.BrandNotValidFieldException;
import com.stock_service.stock.domain.category.exception.ex.CategoryAlreadyExistException;
import com.stock_service.stock.domain.category.exception.ex.CategoryNotValidFieldException;
import com.stock_service.stock.domain.error.ErrorDetail;
import com.stock_service.stock.domain.util.GlobalExceptionMessage;
import com.stock_service.stock.infra.exception.ex.NotificationException;
import io.jsonwebtoken.JwtException;
import jakarta.transaction.RollbackException;
import jakarta.validation.ConstraintViolationException;
import org.hibernate.TransactionException;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.stock_service.stock.domain.util.GlobalExceptionMessage.INVALID_TOKEN;

@ControllerAdvice
public class AdviceHandler {

    @ExceptionHandler(RollbackException.class)
    public ResponseEntity<Object> handleRollbackException(RollbackException ex, WebRequest request) {
        return ResponseEntity.status(400).body("Stock operation error: " + ex.getMessage());
    }

    @ExceptionHandler(NotificationException.class)
    public ResponseEntity<Object> handleNotificationException(NotificationException ex, WebRequest request) {
        return ResponseEntity.status(400).body("Failed to send notification for stock operation: " + ex.getMessage());
    }

    @ExceptionHandler(TransactionException.class)
    public ResponseEntity<Object> handleTransactionException(TransactionException ex, WebRequest request) {
        return ResponseEntity.status(500).body("Database transaction error: " + ex.getMessage());
    }

    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<Object> handleDataAccessException(DataAccessException ex, WebRequest request) {
        return ResponseEntity.status(500).body("Database access error: " + ex.getMessage());
    }

    //Category
    @ExceptionHandler(CategoryAlreadyExistException.class)
    public ResponseEntity<ExceptionDetails> handleCategoryAlreadyExistException(CategoryAlreadyExistException ex, WebRequest request) {
        ExceptionDetails details = new ExceptionDetails(
                HttpStatus.CONFLICT.value(),
                HttpStatus.CONFLICT.getReasonPhrase(),
                ex.getMessage(),
                request.getDescription(false),
                LocalDateTime.now(),
                null
        );

        return new ResponseEntity<>(details, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(CategoryNotValidFieldException.class)
    public ResponseEntity<ExceptionDetails> handleMaxLengthExceededException(CategoryNotValidFieldException ex) {
        ExceptionDetails details = new ExceptionDetails(
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                ex.getMessage(),
                "",
                LocalDateTime.now(),
                null
        );

        return new ResponseEntity<>(details, HttpStatus.BAD_REQUEST);
    }

    //Brand
    @ExceptionHandler(BrandAlreadyExistException.class)
    public ResponseEntity<ExceptionDetails> handleCategoryAlreadyExistException(BrandAlreadyExistException ex, WebRequest request) {
        ExceptionDetails details = new ExceptionDetails(
                HttpStatus.CONFLICT.value(),
                HttpStatus.CONFLICT.getReasonPhrase(),
                ex.getMessage(),
                request.getDescription(false),
                LocalDateTime.now(),
                null
        );

        return new ResponseEntity<>(details, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(BrandNotValidFieldException.class)
    public ResponseEntity<ExceptionDetails> handleMaxLengthExceededException(BrandNotValidFieldException ex) {
        ExceptionDetails details = new ExceptionDetails(
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                ex.getMessage(),
                "",
                LocalDateTime.now(),
                null
        );

        return new ResponseEntity<>(details, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BrandNotFoundByIdException.class)
    public ResponseEntity<ExceptionDetails> handleCategoriesNotFoundByIdsException(BrandNotFoundByIdException ex) {
        ExceptionDetails details = new ExceptionDetails(
                HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND.getReasonPhrase(),
                ex.getMessage(),
                "",
                LocalDateTime.now(),
                null
        );

        return new ResponseEntity<>(details, HttpStatus.NOT_FOUND);
    }

    //Auth
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ExceptionDetails> handleAccessDeniedException(AccessDeniedException ex) {
        ExceptionDetails details = new ExceptionDetails(
                HttpStatus.FORBIDDEN.value(),
                HttpStatus.FORBIDDEN.getReasonPhrase(),
                GlobalExceptionMessage.BAD_ROLE,
                "",
                LocalDateTime.now(),
                null
        );

        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(details);
    }

    //Jwt
    @ExceptionHandler(JwtException.class)
    public ResponseEntity<ExceptionDetails> handleJwtException(JwtException ex) {
        ExceptionDetails details = new ExceptionDetails(
                HttpStatus.UNAUTHORIZED.value(),
                HttpStatus.UNAUTHORIZED.getReasonPhrase(),
                INVALID_TOKEN,
                ex.getMessage(),
                LocalDateTime.now(),
                null
        );
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(details);
    }

    //General
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ExceptionDetails> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        List<ErrorDetail> errorsDetails = new ArrayList<>();

        if (ex.getCause() instanceof MismatchedInputException mie) {
            String fieldName = !mie.getPath().isEmpty() ? mie.getPath().get(0).getFieldName() : "Unknown";
            String requiredType = mie.getTargetType() != null ? mie.getTargetType().getSimpleName() : "Unknown";
            String message = String.format(GlobalExceptionMessage.INVALID_TYPE_PARAM,
                    Objects.requireNonNull(fieldName),
                    Objects.requireNonNull(requiredType));

            errorsDetails.add(new ErrorDetail(fieldName, message));
        }

        ExceptionDetails details = new ExceptionDetails(
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                GlobalExceptionMessage.INVALID_JSON,
                "",
                LocalDateTime.now(),
                errorsDetails
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(details);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionDetails> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {

        List<ErrorDetail> errorsDetails = ex.getBindingResult().getFieldErrors().stream()
                .map(fieldError -> new ErrorDetail(fieldError.getField(), fieldError.getDefaultMessage()))
                .distinct()
                .toList();

        ExceptionDetails details = new ExceptionDetails(
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                GlobalExceptionMessage.INVALID_OBJECT,
                "",
                LocalDateTime.now(),
                errorsDetails
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(details);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ExceptionDetails> handleConstraintViolationException(ConstraintViolationException ex) {

        List<ErrorDetail> errorsDetails = ex.getConstraintViolations().stream()
                .map(constraintViolation -> {
                    String fieldName = constraintViolation.getPropertyPath().toString();
                    fieldName = fieldName.contains(".")
                            ? fieldName.substring(fieldName.lastIndexOf('.') + 1)
                            : fieldName;
                    return new ErrorDetail(fieldName, constraintViolation.getMessage());
                })
                .toList();

        ExceptionDetails details = new ExceptionDetails(
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                GlobalExceptionMessage.INVALID_PARAMETERS,
                "",
                LocalDateTime.now(),
                errorsDetails
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(details);
    }
}
