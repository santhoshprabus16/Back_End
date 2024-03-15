package com.login.login.service;

import com.login.login.models.User;
import com.login.login.repository.UserRepository;
import com.login.login.security.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

        private final UserRepository userRepository;


        private final JwtService jwtService;

        private final AuthenticationManager authenticationManager;

        public AuthService(UserRepository userRepository, JwtService jwtService, AuthenticationManager authenticateManager) {
            this.userRepository = userRepository;
            this.jwtService = jwtService;
            this.authenticationManager = authenticateManager;
        }
        public AuthenticationResponse login(User req){
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    req.getUsername(),
                    req.getPassword()
            ));
            User user =userRepository.findByUsername(req.getUsername());
            String token =jwtService.generateToken(String.valueOf(user));

            return new AuthenticationResponse(token);

    }
}
