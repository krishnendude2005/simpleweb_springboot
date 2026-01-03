package com.krishnendu.SimpleWebApp.controller;

import com.krishnendu.SimpleWebApp.model.Users;
import com.krishnendu.SimpleWebApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {


    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }


    @PostMapping("/register")
    public Users register(@RequestBody Users user) {

        return service.register(user);
    }

    @PostMapping("/login")
    public String login(@RequestBody Users user) {


        return service.verify(user);
    }
}
