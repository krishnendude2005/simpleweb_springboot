package com.krishnendu.SimpleWebApp.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;

public class RobotAuthenticationFilter extends OncePerRequestFilter  {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 1. decide whether we want to apply the filter
        if (!Collections.list(request.getHeaderNames()).contains("x-robot-filter")) {
            filterChain.doFilter(request, response);
            return;
        }

        // 2. check credentials [authenticate | reject]

        if (!request.getHeader("x-robot-filter").equals("robot")) {
            // reject
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write("⛔⛔🤖🤖🤖🤖You are not robot🤖🤖🤖🤖⛔⛔");
            return; // returning from here because we do not want to continue with this request
        }
        // authenticate
        var auth = new RobotAuthenticationToken();
        var newContext = SecurityContextHolder.createEmptyContext();
        newContext.setAuthentication(auth);
        SecurityContextHolder.setContext(newContext);

        // 3. call next
        filterChain.doFilter(request, response);
    }
}
