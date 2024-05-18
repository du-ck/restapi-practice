package com.gritstandard.project.common.exception.custom;

/**
 * 조회 결과가 없을 경우 발생하는 Exception
 */
public class ResultNotFoundException extends RuntimeException {
    public ResultNotFoundException(String message) {
        super(message);
    }
}
