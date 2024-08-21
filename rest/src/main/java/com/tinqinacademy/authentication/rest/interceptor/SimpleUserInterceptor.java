package com.tinqinacademy.authentication.rest.interceptor;


import com.tinqinacademy.authentication.api.mappings.MappingConstants;
import com.tinqinacademy.authentication.core.util.JwtService;
import com.tinqinacademy.authentication.persistence.entities.UserEntity;
import com.tinqinacademy.authentication.persistence.enums.RoleType;
import com.tinqinacademy.authentication.persistence.repositories.UserRepository;
import com.tinqinacademy.authentication.rest.credentials.LoggedUser;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.IOException;
import java.util.Optional;

@Component
public class SimpleUserInterceptor implements HandlerInterceptor {
private final LoggedUser loggedUser;
private final JwtService jwtService;
private final UserRepository userRepository;

    public SimpleUserInterceptor(LoggedUser loggedUser, JwtService jwtService, UserRepository userRepository) {
        this.loggedUser = loggedUser;
        this.jwtService = jwtService;
        this.userRepository = userRepository;
    }

    @Override
public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
    String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);
    if (authorization == null || !authorization.startsWith("Bearer ")) {
        response.sendError(HttpServletResponse.SC_FORBIDDEN);
        return false;
    }

    String jwtToken = authorization.substring(7);
    if (!jwtService.isTokenValid(jwtToken)) {
        response.sendError(HttpServletResponse.SC_FORBIDDEN);
        return false;
    }

    String username = jwtService.extractUsername(jwtToken);
    Optional<UserEntity> userOptional = userRepository.findByUsername(username);
    if (userOptional.isEmpty()) {
        response.sendError(HttpServletResponse.SC_FORBIDDEN);
        return false;
    }

    if (isAdminPath(request.getRequestURI()) && !userOptional.get().getRoleType().equals(RoleType.ADMIN)) {
        response.sendError(HttpServletResponse.SC_FORBIDDEN);
        return false;
    }

    loggedUser.setLoggedUser(userOptional.get());
    loggedUser.setToken(jwtToken);
    return true;
}


private boolean isAdminPath(String requestUri) {
    AntPathMatcher antPathMatcher = new AntPathMatcher();
    return MappingConstants.adminPaths.stream()
            .anyMatch(path -> antPathMatcher.match(path, requestUri));
}
}