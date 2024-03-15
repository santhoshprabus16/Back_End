package com.login.login.controller;

import com.login.login.models.User;
import com.login.login.repository.UserRepository;
import com.login.login.security.JwtService;
import com.login.login.service.AuthService;
import com.login.login.service.AuthenticationResponse;
import com.login.login.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.naming.AuthenticationException;

@RestController
public class AuthController {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private final AuthService authService;

    public AuthController(AuthService authService) {

        this.authService = authService;
    }


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User request) throws AuthenticationException {
        User user=userRepository.findByUsername(request.getUsername());
        if(user==null || !passwordEncoder.matches(request.getPassword(),user.getUsername())){
            throw new AuthenticationException("Invalid username or password");
        }
        String token =jwtService.generateToken(user.getUsername());
        return ResponseEntity.ok(login(user));

    }


}
