package com.crtneko.springsecurity.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.crtneko.springsecurity.config.AppPasswordConfig;
import com.crtneko.springsecurity.models.UserEntity;
import com.crtneko.springsecurity.models.repository.UserRepository;

@RestController
@RequestMapping("/api")
public class UserRestController {

    private final AppPasswordConfig appPasswordConfig;
    private final UserRepository userRepository;

    @Autowired
    public UserRestController(AppPasswordConfig appPasswordConfig, UserRepository userRepository) {
        this.appPasswordConfig = appPasswordConfig;
        this.userRepository = userRepository;
    }

    @PostMapping("/user")
    public ResponseEntity<UserEntity> createNewUser(@RequestBody UserEntity newUser) {

        UserEntity userEntity = new UserEntity(
                newUser.getUsername(),
                appPasswordConfig.bCryptPasswordEncoder().encode(newUser.getPassword()),
                List.of(),
                newUser.isAccountNonExpired(),
                newUser.isAccountNonLocked(),
                newUser.isEnabled(),
                newUser.isCredentialsNonExpired()
        );

        return new ResponseEntity<>(userRepository.save(userEntity), HttpStatus.CREATED);
    }


    @GetMapping("/hash")
    public ResponseEntity<String> testBcryptHash(
            @RequestParam(defaultValue = "", required = false) String continueParam,
            @RequestParam(defaultValue = "", required = false) String inputPassword
    ) {

        return new ResponseEntity<>(
                appPasswordConfig.bCryptPasswordEncoder().encode(inputPassword),
                HttpStatus.ACCEPTED
        );
    }
   
    // TODO - Den här behövs inte.
//    @PreAuthorize("hasRole('ADMIN')")
//    @GetMapping("/admin-page")
//    public String adminPage() {
//        // Your controller logic
//        return "admin-page";
//    }


    @GetMapping("/helloAdmin")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<String> sayHelloToAdmin() {

        return new ResponseEntity<>("Hello ADMIN!", HttpStatus.ACCEPTED);
    }

    @GetMapping("/helloUser")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<String> sayHelloToUser() {

        return new ResponseEntity<>("Hello USER!", HttpStatus.ACCEPTED);
    }

    // TODO - think it dosn't get the permition get from roles need to check up on that so for now i do hasRole etc underneath
//    @GetMapping("/sayGet")
//    @PreAuthorize("hasAuthority('GET')")
//    public ResponseEntity<String> checkGetAuthority() {
//
//        return new ResponseEntity<>("You can only enter with GET Authority!", HttpStatus.ACCEPTED);
//    }
    
    @GetMapping("/sayGet")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    public ResponseEntity<String> checkGetAuthority() {
        return new ResponseEntity<>("You can only enter with GET Authority!", HttpStatus.ACCEPTED);
    }




}