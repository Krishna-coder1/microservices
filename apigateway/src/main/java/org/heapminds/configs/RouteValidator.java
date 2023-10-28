package org.heapminds.configs;

import java.util.List;
import java.util.function.Predicate;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Service;

@Service
public class RouteValidator {
    public static final List<String> openEndPoints = List.of("auth/register","auth/login");

    public Predicate<ServerHttpRequest> isSecure = request->openEndPoints.stream()
            .noneMatch(url->request.getURI().getPath().contains(url));
}
