package com.gritstandard.project.common.data;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

/**
 * response data 형식
 */
@Data
@Builder
public class ResponseData {
    private ErrorResponse error;
    private HttpStatus status;
    private Object data;


}
