package com.krishnendu.SimpleWebApp.config;

import org.jspecify.annotations.Nullable;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;

public class DanielAuthenticationProvider implements AuthenticationProvider {
    @Override
    public @Nullable Authentication authenticate(Authentication authentication) throws AuthenticationException {
        if (authentication.getName().equals("daniel")) {
            // ok
            var daniel = User.withUsername("daniel")
                    .password("daniel-passowrd")
                    .roles("USER", "ADMIN")
                    .build();

            // return an authenticated authentication object
            return UsernamePasswordAuthenticationToken.authenticated(
                    daniel,
                    null,
                    daniel.getAuthorities()
            );
        }
        //
        return null;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
