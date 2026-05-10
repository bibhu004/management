package com.order.management.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.order.management.DTO.UserDTO;
import com.order.management.entity.User;
import com.order.management.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User adduser(UserDTO userDTO){
        try {
            User user = new User();
            user.setName(userDTO.getName());
            user.setEmail(userDTO.getEmail());
            User savedUser = userRepository.save(user);
            return savedUser; 
        } catch (Exception e) {
            throw new RuntimeException("Failed to add user");
        }
    }
}
