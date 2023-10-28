package org.heapminds.configs;

import java.nio.charset.StandardCharsets;

import org.heapminds.utils.JwtTokenGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RefreshScope
@Component
public class AuthenticationFilter implements GatewayFilter {
    @Autowired
    private RouteValidator routeValidator;

    @Autowired
    private JwtTokenGenerator jwtTokenGenerator;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest httpRequest = exchange.getRequest();
        String url = httpRequest.getURI().getPath();
        if (url.contains("api") || url.contains("actuator") || url.contains("images")) {
            return chain.filter(exchange);
        }
        if (routeValidator.isSecure.test(httpRequest)) {
            if (authMissing(exchange)) {
                return onError(exchange, HttpStatus.UNAUTHORIZED);
            } else {
                final String jwtToken = httpRequest.getHeaders().getOrEmpty("Authorization").get(0).substring(7);
                if (!jwtTokenGenerator.isValidToken(jwtToken)) {
                    return onError(exchange, HttpStatus.UNAUTHORIZED);
                }
            }
        }
        return chain.filter(exchange);
    }

    private Mono<Void> onError(ServerWebExchange exchange, HttpStatus status) {
        ServerHttpResponse response = exchange.getResponse();
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON); // Set appropriate content type

        // Create a JSON response with an error message
        String responseBody = "{\"error\":\"Unauthorized\"}";

        // Write the response body
        DataBuffer buffer = response.bufferFactory().wrap(responseBody.getBytes(StandardCharsets.UTF_8));
        // Set the status code
        response.setStatusCode(HttpStatus.UNAUTHORIZED);

        // Write the response body and then set it as complete
        return response.writeWith(Flux.just(buffer));
    }

    private Boolean authMissing(ServerWebExchange exchange) {
        return !exchange.getRequest().getHeaders().containsKey("Authorization");
    }
}
