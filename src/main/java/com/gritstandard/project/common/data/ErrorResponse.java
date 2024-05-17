package com.gritstandard.project.common.data;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ErrorResponse {
    private String errorCode;
    private String errorText;

    public static ErrorResponse createDefault() {
        return ErrorResponse.builder()
                .errorCode("")
                .errorText("")
                .build();
    }
}
