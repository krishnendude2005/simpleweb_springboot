package com.krishnendu.SimpleWebApp.controller;

import com.krishnendu.SimpleWebApp.model.UserPrincipal;
import com.krishnendu.SimpleWebApp.model.Users;
import com.krishnendu.SimpleWebApp.service.MyUserDetailService;
import com.krishnendu.SimpleWebApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
public class UserController {


    private final UserService service;
    private final MyUserDetailService myUserDetailService;

    public UserController(UserService service, MyUserDetailService myUserDetailService) {
        this.service = service;
        this.myUserDetailService = myUserDetailService;
    }


    @PostMapping("/register")
    public Users register(@RequestBody Users user) {

        return service.register(user);
    }

    @PostMapping("/login")
    public String login(@RequestBody Users user) {


        return service.verify(user);
    }
    @GetMapping("/get-user-details")
    public ResponseEntity<Object> getUserDetails() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserPrincipal userPrincipal = (UserPrincipal) principal;
        System.out.println(userPrincipal.isAccountNonExpired());
   return new ResponseEntity<>(Objects.requireNonNull(SecurityContextHolder.getContext().getAuthentication()).getPrincipal(), HttpStatus.OK);
    }
}
