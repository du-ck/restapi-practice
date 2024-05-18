package com.gritstandard.project.common.exception.custom;

/**
 * 권한이 필요한 api 요청 시 권한이 없을 경우 발생하는 Exception
 */
public class UnauthorizedAccessException extends RuntimeException {
    public UnauthorizedAccessException(String message) {
        super(message);
    }
}
