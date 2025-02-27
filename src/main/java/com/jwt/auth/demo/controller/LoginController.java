package com.jwt.auth.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jwt.auth.demo.dto.LoginRequest;
import com.jwt.auth.demo.model.Person;
import com.jwt.auth.demo.service.AuthService;
import com.jwt.auth.demo.service.PersonService;

@RestController
@RequestMapping("/api/auth/")
public class LoginController {

    @Autowired
    private AuthService authService;
    
    @Autowired
    private PersonService personService;

    @PostMapping("login")
    public String login(@RequestBody LoginRequest loginRequest) {
        Person person = authService.login(loginRequest.getUsername(), loginRequest.getPassword());

        if (person != null) {
            return authService.generateToken(person);
        }

        return "Invalid credentials";
    }

    @PostMapping("logout")
    public String logout() {
        SecurityContextHolder.clearContext();
        return "Logged out successfully";
    }
}


