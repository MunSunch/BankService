package com.munsun.deal.aspects;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
@Component
public class RequestResponseInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("{} {}?{} {}", request.getMethod(), request.getRequestURL(), request.getQueryString(), request.getProtocol());
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        log.info("{} {}?{} {}", request.getMethod(), request.getRequestURL(), request.getQueryString(), request.getProtocol());
        if(ex != null) {
            log.error("Error in request {}", ex.getMessage());
            return;
        }
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
