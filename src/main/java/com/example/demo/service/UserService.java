package com.example.demo.service;

import com.example.demo.entity.Alojamiento;
import com.example.demo.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    Optional<User> login(String email, String password);
    
    User register(User user);
    
    
    String assignRole(Long userId, Long roleId);  

    
   
    
}
