package com.learning.api_gateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

    @Bean
    public RouteLocator gatewayRouters(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("auth-service-router", r -> r.path("/api/auth/**")
                        .uri("http://localhost:8012"))
                .route("users-service-router", r -> r.path("/api/users/**")
                        .uri("http://localhost:8013"))
                .route("core-service-router", r -> r.path("/api/**")
                        .uri("http://localhost:8011"))

                .build();
    }
}
