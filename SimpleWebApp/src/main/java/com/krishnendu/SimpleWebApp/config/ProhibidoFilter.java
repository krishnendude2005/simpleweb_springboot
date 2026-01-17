package com.krishnendu.SimpleWebApp.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class ProhibidoFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
       // System.out.println("⛔⛔⛔⛔⛔⛔⛔⛔⛔");

        if ("si".equals(request.getHeader("x-prohibido"))) {
            // reject the request
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write("⛔⛔⛔⛔⛔⛔⛔⛔⛔es prohibido⛔⛔⛔⛔⛔⛔⛔⛔⛔");
            return; // returning from here because we do not want to continue with this request
        }

        filterChain.doFilter(request, response);
    }
}
