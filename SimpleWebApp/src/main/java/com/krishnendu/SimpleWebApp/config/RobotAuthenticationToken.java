package com.krishnendu.SimpleWebApp.config;

import org.jspecify.annotations.Nullable;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;

public class RobotAuthenticationToken extends AbstractAuthenticationToken {
    public RobotAuthenticationToken() {
        super(AuthorityUtils.createAuthorityList("ROLE_ROBOT"));
    }

    @Override
    public @Nullable Object getCredentials() {
        return null;
    }

    @Override
    public @Nullable Object getPrincipal() {
        return "Hello, I am Robot";
    }

    @Override
    public boolean isAuthenticated() {
        return true;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) {
        throw new RuntimeException("Can't modify this");
    }
}
