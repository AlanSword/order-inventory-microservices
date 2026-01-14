package com.example.apigateway;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.HandlerMapping;

import java.util.Map;

@Component
public class RouteLoggingInterceptor implements HandlerInterceptor {

    private static final Logger log = LoggerFactory.getLogger(RouteLoggingInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) {

        String method = request.getMethod();
        String path = request.getRequestURI();

        @SuppressWarnings("unchecked")
        Map<String, Object> attrs =
                (Map<String, Object>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);

        // For simple Path=/api/orders/** style routes this may contain variables; log basic info
        log.info("Gateway routing request: {} {} | attributes={}", method, path, attrs);
        return true;
    }
}