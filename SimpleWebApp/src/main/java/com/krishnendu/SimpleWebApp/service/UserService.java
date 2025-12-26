package com.krishnendu.SimpleWebApp.service;

import com.krishnendu.SimpleWebApp.model.Users;
import com.krishnendu.SimpleWebApp.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepo repo;

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    public Users register(Users user) {
        String hashedPassword = encoder.encode(user.getPassword());
        user.setPassword(hashedPassword);
        System.out.println("Password before Hashing : " + user.getPassword() + "\n" + "Password after Hashing : " + hashedPassword);
        return repo.save(user);
    }
}
