package com.gritstandard.project.common.exception.custom;

/**
 * Required = true 인 파라미터의 value 가 안들어왔을 경우 발생하는 Exception
 */
public class MissingParameterException extends RuntimeException {
    public MissingParameterException(String message) {
        super(message);
    }
}
