package com.krishnendu.SimpleWebApp.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.AuthorizationFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {


    private final UserDetailsService userDetailsService;
    private final jwtFilter jwtFilter;

    public SecurityConfig(UserDetailsService userDetailsService, jwtFilter jwtFilter) {
        this.userDetailsService = userDetailsService;
        this.jwtFilter = jwtFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        return http
                .csrf(csrf -> csrf.disable())


                .formLogin(Customizer.withDefaults())

                // authorization filter is the filter that does this
                .authorizeHttpRequests(request -> request
                        .requestMatchers("/register", "/login", "/oauth2/**").permitAll()
                        .anyRequest().authenticated())

                // oauth2 login
                .oauth2Login(Customizer.withDefaults())

                .httpBasic(Customizer.withDefaults()) // for postman ( REST Api access )

                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED))  // stateless

                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)

                .addFilterBefore(new ProhibidoFilter(), AuthorizationFilter.class) // my custom filter - ProhibidoFilter

                .addFilterBefore(new RobotAuthenticationFilter(), AuthorizationFilter.class) // my custom filter - RobotAuthenticationFilter

                .authenticationProvider(new DanielAuthenticationProvider()) // my custom authentication provider - DanielAuthenticationProvider

                .build();


    }


    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider(userDetailsService);
        provider.setPasswordEncoder(new BCryptPasswordEncoder(12));
        return provider;
    }

    @Bean
    public AuthenticationManager authicationManager(AuthenticationConfiguration config) {
        return config.getAuthenticationManager();
    }

}
