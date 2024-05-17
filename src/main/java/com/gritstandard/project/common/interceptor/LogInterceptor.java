package com.gritstandard.project.common.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * 요청 관련 로깅을 위한 인터셉터
 */
@Slf4j
public class LogInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("Request URL: {}", request.getRequestURL());
        request.getParameterMap().forEach((key, value) -> log.info("Request Parameters: {}: {}", key, String.join(",", value)));
        /*log.info("Request Parameters:: {}", ToStringBuilder.reflectionToString(request.getParameterMap(), ToStringStyle.MULTI_LINE_STYLE));*/

        return true;
    }
}
