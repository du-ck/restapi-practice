package com.gritstandard.project.common.exception;

import com.gritstandard.project.common.data.ErrorResponse;
import com.gritstandard.project.common.data.ResponseData;
import com.gritstandard.project.common.exception.custom.MissingParameterException;
import com.gritstandard.project.common.exception.custom.ResultNotFoundException;
import com.gritstandard.project.common.exception.custom.UnauthorizedAccessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 전역 Exception 관리
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Object> handleIllegalArgument(IllegalArgumentException e) {
        ResponseData responseData = ResponseData.builder()
                .error(ErrorResponse.builder()
                        .errorCode("400")
                        .errorText(e.getMessage())
                        .build())
                .status(HttpStatus.BAD_REQUEST)
                .data("").build();

        log.error("IllegalArgumentException", e);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseData);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Object> handleDataIntegrityViolation(DataIntegrityViolationException e) {
        ResponseData responseData = ResponseData.builder()
                .error(ErrorResponse.builder()
                        .errorCode("400")
                        .errorText(e.getMessage())
                        .build())
                .status(HttpStatus.BAD_REQUEST)
                .data("").build();

        log.error("DataIntegrityViolationException", e);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseData);
    }

    @ExceptionHandler(DataAccessResourceFailureException.class)
    public ResponseEntity<Object> handleDataAccessResourceFailure(DataAccessResourceFailureException e) {
        ResponseData responseData = ResponseData.builder()
                .error(ErrorResponse.builder()
                        .errorCode("500")
                        .errorText(e.getMessage())
                        .build())
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .data("").build();

        log.error("DataAccessResourceFailureException", e);

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseData);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleException(Exception e) {
        ResponseData responseData = ResponseData.builder()
                .error(ErrorResponse.builder()
                        .errorCode("500")
                        .errorText(e.getMessage())
                        .build())
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .data("").build();

        log.error("Exception", e);

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseData);
    }


    /**
     * Required = true 인 파라미터의 value 가 안들어왔을 경우
     * @param e
     * @return
     */
    @ExceptionHandler(MissingParameterException.class)
    public ResponseEntity<Object> handleMissingParameter(MissingParameterException e) {
        ResponseData responseData = ResponseData.builder()
                .error(ErrorResponse.builder()
                        .errorCode("500")
                        .errorText(e.getMessage())
                        .build())
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .data("").build();

        log.error("MissingParameterException", e);

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseData);
    }

    /**
     * 권한이 필요한 api 요청 시 권한이 없을 경우
     * @param e
     * @return
     */
    @ExceptionHandler(UnauthorizedAccessException.class)
    public ResponseEntity<Object> handleUnauthorizedAccess(UnauthorizedAccessException e) {
        ResponseData responseData = ResponseData.builder()
                .error(ErrorResponse.builder()
                        .errorCode("403")
                        .errorText(e.getMessage())
                        .build())
                .status(HttpStatus.FORBIDDEN)
                .data("").build();

        log.error("UnauthorizedAccessException", e);

        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(responseData);
    }

    /**
     * 조회 결과가 없을 경우
     * @param e
     * @return
     */
    @ExceptionHandler(ResultNotFoundException.class)
    public ResponseEntity<Object> handleResultNotFound(ResultNotFoundException e) {
        ResponseData responseData = ResponseData.builder()
                .error(ErrorResponse.builder()
                        .errorCode("404")
                        .errorText(e.getMessage())
                        .build())
                .status(HttpStatus.NOT_FOUND)
                .data("").build();

        log.error("ResultNotFoundException", e);

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseData);
    }
}
