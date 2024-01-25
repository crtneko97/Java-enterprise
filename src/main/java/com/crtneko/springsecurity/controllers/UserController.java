package com.crtneko.springsecurity.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.crtneko.springsecurity.config.AppPasswordConfig;
import com.crtneko.springsecurity.models.Roles;
import com.crtneko.springsecurity.models.UserEntity;
import com.crtneko.springsecurity.models.repository.UserRepository;

import jakarta.validation.Valid;

@Controller
public class UserController {

    private final UserRepository userRepository;
    private final AppPasswordConfig appPasswordConfig; // Bcrypt

    @Autowired
    public UserController(UserRepository userRepository, AppPasswordConfig appPasswordConfig) {
        this.userRepository = userRepository;
        this.appPasswordConfig = appPasswordConfig;
    }

    @GetMapping("/register")
    public String registerUserPage(UserEntity userEntity) {

        return "register";
    }
    
    // Add model and more to create a drop down for user Role.
    @PostMapping("/register")
    public String registerUser(
            @Valid UserEntity userEntity,
            BindingResult result
    ) {
        if (result.hasErrors()) {
            return "register";
        }

        // Set the role for the user (e.g., USER role for a regular user)
        userEntity.setRoles(Roles.USER);

        userEntity.setPassword(
                appPasswordConfig.bCryptPasswordEncoder().encode(userEntity.getPassword())
        );

        userEntity.setAccountEnabled(true);
        userEntity.setAccountNonLocked(true);
        userEntity.setAccountNonExpired(true);
        userEntity.setCredentialsNonExpired(true);

        userRepository.save(userEntity);

        return "redirect:/login";
    }

    

}