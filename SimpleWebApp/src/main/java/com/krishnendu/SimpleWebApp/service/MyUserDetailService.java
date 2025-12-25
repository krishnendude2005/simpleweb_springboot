package com.krishnendu.SimpleWebApp.service;

import com.krishnendu.SimpleWebApp.model.UserPrincipal;
import com.krishnendu.SimpleWebApp.model.Users;
import com.krishnendu.SimpleWebApp.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailService implements UserDetailsService {

    @Autowired
    private UserRepo repo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Users user = repo.findByUsername(username);
 if(user == null){
     System.out.println("User not found");
     throw new UsernameNotFoundException("User not found");
 }
        return new UserPrincipal(user);
    }
}
