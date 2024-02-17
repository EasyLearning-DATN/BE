package com.poly.easylearning.exception;

import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import com.poly.easylearning.constant.ResourceBundleConstant;
import com.poly.easylearning.constant.SystemConstant;
import com.poly.easylearning.utils.ResponseUtil;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@ControllerAdvice
@RequiredArgsConstructor
public class AppExceptionHandler {

    private final ResponseUtil responseUtil;

    @ExceptionHandler(value = ApiRequestException.class)
    public ResponseEntity<Object> handleException(ApiRequestException e) {
        HttpStatus badRequest = HttpStatus.BAD_REQUEST;
        AppException exception = new AppException(
                e.getCode(),
                SystemConstant.STATUS_CODE_BAD_REQUEST,
                responseUtil.getMessageBundle(e.getCode()),
                responseUtil.currentTimeSeconds()
        );
        return new ResponseEntity<>(exception, badRequest);
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity<Object> handleBindException(BindException e) {
        HttpStatus badRequest = HttpStatus.BAD_REQUEST;
        List<String> listError = new ArrayList<>();

        if (e.getBindingResult().hasErrors()) {
            listError = e.getBindingResult().getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .map(responseUtil::getMessageBundle)
                    .toList();
        }
        AppException exception = new AppException(
                ResourceBundleConstant.ERROR_VALIDATION_FIELD,
                SystemConstant.STATUS_CODE_BAD_REQUEST,
                listError.toString(),
                responseUtil.currentTimeSeconds()
        );
        return new ResponseEntity<>(exception, badRequest);
    }

    @ExceptionHandler({ConstraintViolationException.class, TransactionSystemException.class})
    public ResponseEntity<Object> handleException(ConstraintViolationException e) {
        String code = e.getConstraintViolations().iterator().next().getMessage();
        AppException exception = new AppException(
                code,
                SystemConstant.STATUS_CODE_BAD_REQUEST,
                responseUtil.getMessageBundle(code),
                responseUtil.currentTimeSeconds()
        );
        return new ResponseEntity<>(exception, HttpStatusCode.valueOf(SystemConstant.STATUS_CODE_BAD_REQUEST));
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationExceptions(MethodArgumentNotValidException ex) {
        String errorMessage = Objects.requireNonNull(ex.getBindingResult().getFieldError()).getDefaultMessage();
        HttpStatus internalServerError = HttpStatus.INTERNAL_SERVER_ERROR;
        AppException exception = new AppException(
                ResourceBundleConstant.ERROR_SERVER,
                SystemConstant.STATUS_CODE_INTERNAL,
                errorMessage,
                responseUtil.currentTimeSeconds()
        );
        return new ResponseEntity<>(exception, internalServerError);
    }

//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<Object> handleException(Exception e) {
//        AppException exception = new AppException(
//                ResourceBundleConstant.SYS_1001,
//                SystemConstant.STATUS_CODE_BAD_REQUEST,
//                responseUtil.getMessageBundle(ResourceBundleConstant.SYS_1001),
//                responseUtil.currentTimeSeconds()
//        );
//        return new ResponseEntity<>(exception, HttpStatusCode.valueOf(SystemConstant.STATUS_CODE_BAD_REQUEST));
//    }

    @ExceptionHandler({MismatchedInputException.class})
public ResponseEntity<Object> handleException(MismatchedInputException e) {
        AppException exception = new AppException(
                ResourceBundleConstant.SYS_1002,
                SystemConstant.STATUS_CODE_BAD_REQUEST,
                responseUtil.getMessageBundle(ResourceBundleConstant.SYS_1002),
                responseUtil.currentTimeSeconds()
        );
        return new ResponseEntity<>(exception, HttpStatusCode.valueOf(SystemConstant.STATUS_CODE_BAD_REQUEST));
    }

    @ExceptionHandler(EnumAppNotFoundException.class)
    public ResponseEntity<Object> handleException(EnumAppNotFoundException e) {
        AppException exception = new AppException(
                e.getCode(),
                SystemConstant.STATUS_CODE_BAD_REQUEST,
                responseUtil.getMessageBundle(e.getCode()),
                responseUtil.currentTimeSeconds()
        );
        return new ResponseEntity<>(exception, HttpStatusCode.valueOf(SystemConstant.STATUS_CODE_BAD_REQUEST));
    }

}