package com.krishnendu.SimpleWebApp.service;

import com.krishnendu.SimpleWebApp.model.Users;
import com.krishnendu.SimpleWebApp.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {


    private final UserRepo repo;
    private final AuthenticationManager authManager;
    private final JWTService jwtService;

    public UserService(UserRepo repo, AuthenticationManager authManager, JWTService jwtService) {
        this.repo = repo;
        this.authManager = authManager;
        this.jwtService = jwtService;
    }

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    public Users register(Users user) {
        String hashedPassword = encoder.encode(user.getPassword());
        user.setPassword(hashedPassword);
        System.out.println("Password before Hashing : " + user.getPassword() + "\n" + "Password after Hashing : " + hashedPassword);
        return repo.save(user);
    }

    public String verify(Users user) {

        Authentication authentication =
                authManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));

        if (authentication.isAuthenticated()) {
            return jwtService.generateToken(user.getUsername());
        }

        return "Fail";

    }
}
