package com.example.apigateway.gateway;

import com.example.apigateway.gateway.filters.AuthenticatedUserHeaderFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;

import static org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions.route;
import static org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions.http;

@Configuration
public class GatewayRoutes {

    @Bean
    RouterFunction<ServerResponse> routes(AuthenticatedUserHeaderFilter authHeaderFilter) {

        RouterFunction<ServerResponse> users =
                route("user-service")
                        .route(req -> req.path().startsWith("/api/users"), http("http://user-management:8083"))
                        .before(authHeaderFilter.addUsernameHeader())
                        .build();

        RouterFunction<ServerResponse> inventory =
                route("inventory-service")
                        .route(req -> req.path().startsWith("/api/inventory"), http("http://inventory-service:8082"))
                        .before(authHeaderFilter.addUsernameHeader())
                        .build();

        RouterFunction<ServerResponse> orders =
                route("order-service")
                        .route(req -> req.path().startsWith("/api/orders"), http("http://order-service:8081"))
                        .before(authHeaderFilter.addUsernameHeader())
                        .build();

        // Combine all route router-functions into one
        return users.and(inventory).and(orders);
    }
}
