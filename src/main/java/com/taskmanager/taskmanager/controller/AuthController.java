package com.taskmanager.taskmanager.controller;

import com.taskmanager.taskmanager.domain.User;
import com.taskmanager.taskmanager.dto.AuthRequest;
import com.taskmanager.taskmanager.service.JwtService;
import com.taskmanager.taskmanager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired private UserService userService;
    @Autowired private JwtService jwtService;
    @PostMapping("/new")
    public String addNewUser(@RequestBody User user) {
        return userService.addUser(user);
    }
    @PostMapping("/auth")
    public String authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getName(),
                authRequest.getPassword()));
        //  SecurityContextHolder.getContext().setAuthentication(authentication);
        //  Authentication authentication1 = SecurityContextHolder.getContext().getAuthentication();

        if (authentication.isAuthenticated()) {

            String userName = authentication.getName();
            return jwtService.generateToken(userName);
        } else {
            throw new UsernameNotFoundException("Login failed !");
        }
    }
    @GetMapping("/logout")
    public String logOut(){
        SecurityContextHolder.setContext(null);
        //  SecurityContextHolder.getContext().getAuthentication()
        return "logout success ";
    }

}
