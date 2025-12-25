package com.krishnendu.SimpleWebApp.repository;

import com.krishnendu.SimpleWebApp.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<Users,Integer> {
    //
    Users findByUsername(String username);
}
