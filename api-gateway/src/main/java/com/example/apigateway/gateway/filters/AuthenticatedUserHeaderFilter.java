package com.example.apigateway.gateway.filters;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.function.HandlerFilterFunction;
import org.springframework.web.servlet.function.ServerRequest;
import org.springframework.web.servlet.function.ServerResponse;

import java.security.Principal;
import java.util.function.Function;

@Component
public class AuthenticatedUserHeaderFilter {

    public Function<ServerRequest, ServerRequest> addUsernameHeader() {
        return request -> {
            HttpServletRequest servletRequest = request.servletRequest();
            Principal principal = servletRequest.getUserPrincipal();

            String username = (principal != null) ? principal.getName() : "anonymous";

            return ServerRequest.from(request)
                    .headers(h -> h.remove("X-Authenticated-User"))
                    .header("X-Authenticated-User", username)
                    .build();
        };
    }
}
