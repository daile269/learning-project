package com.learning.api_gateway.filter;

import com.learning.api_gateway.exception.AppException;
import com.learning.api_gateway.exception.ErrorCode;
import com.learning.api_gateway.untils.JwtUntil;
import lombok.RequiredArgsConstructor;
import org.apache.dubbo.rpc.RpcContext;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class JwtToDubboMetadataFilter implements GlobalFilter {

    private final JwtUntil jwtUntil;

    private final WebClient.Builder webClientBuilder;

    private final String AUTH_SERVICE_URL = "http://localhost:8012/api/auth/validate";
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        String authHeader = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

        if (authHeader != null && authHeader.startsWith("Bearer ")){
            String token = authHeader.substring(7);
            return validateTokenWithAuthService(token)
                    .then(Mono.defer(() -> {
                        try {
                            String username = jwtUntil.extractUsername(token);
                            String role = jwtUntil.extractRole(token);

                            ServerHttpRequest mutatedRequest = exchange.getRequest()
                                    .mutate()
                                    .header("username", username)
                                    .header("role", role)
                                    .header("token", token)
                                    .build();

                            ServerWebExchange mutatedExchange = exchange.mutate()
                                    .request(mutatedRequest)
                                    .build();

                            return chain.filter(mutatedExchange);
                        } catch (Exception e) {
                            throw new AppException(ErrorCode.TOKEN_VALID);
                        }
                    }));
        }
        return chain.filter(exchange);
    }
    private Mono<Void> validateTokenWithAuthService(String token) {
        return webClientBuilder.baseUrl(AUTH_SERVICE_URL)
                .build()
                .post()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .retrieve()
                .onStatus(status -> status.is4xxClientError(), clientResponse -> {
                    return Mono.error(new AppException(ErrorCode.TOKEN_INVALIDATED));
                })
                .onStatus(status -> status.is5xxServerError(), clientResponse -> {
                    return Mono.error(new AppException(ErrorCode.SERVICE_UNAVAILABLE));
                })
                .bodyToMono(Void.class);
    }
}
