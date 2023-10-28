package org.heapminds.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

@Configuration
public class GatewayConfig {

        @Autowired
        private AuthenticationFilter filter;

        @Bean
        public CorsWebFilter corsWebFilter() {
                CorsConfiguration corsConfig = new CorsConfiguration();
                corsConfig.addAllowedOrigin("https://34b8-103-69-28-122.ngrok-free.app");
                corsConfig.addAllowedMethod("*");
                corsConfig.addAllowedHeader("*");

                corsConfig.setAllowCredentials(true);

                UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
                source.registerCorsConfiguration("/**", corsConfig);

                return new CorsWebFilter(source);
        }

        @Bean
        public RouteLocator routes(RouteLocatorBuilder routeLocatorBuilder) {
                return routeLocatorBuilder.routes()
                                .route("user-service", r -> r.path("/api/v1/test/**", "/api/v1/auth/**", "/images/**")
                                                .filters(f -> f.filter(filter))
                                                .uri("lb://user-service"))
                                .route("admin-server", r -> r.path("/admin/**")
                                                .filters(f -> f.filter(filter))
                                                .uri("http://admin-server"))
                                .route("lb://payment-service",
                                                r -> r.path("/api/handleSubscription/**", "/actuator/**",
                                                                "/api/actuator/**",
                                                                "/images/**",
                                                                "/api/log")
                                                                .filters(f -> f.filter(filter))
                                                                .uri("lb://payment-service"))
                                .route("notification-service", r -> r.path("/api/notification/**", "/images/**")
                                                .filters(f -> f.filter(filter))
                                                .uri("lb://notification-service"))

                                .build();

        }
}