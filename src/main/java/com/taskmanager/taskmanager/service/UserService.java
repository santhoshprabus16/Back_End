package com.taskmanager.taskmanager.service;

import com.taskmanager.taskmanager.domain.Role;
import com.taskmanager.taskmanager.domain.User;
import com.taskmanager.taskmanager.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;


    public String addUser(User user){
        Optional<User> checkEmailExist = userRepository.findByEmail(user.getEmail());
        if(checkEmailExist.isPresent()){
            return "Email already exist";
        }
        else{
            String encodedPassword = passwordEncoder.encode(user.getPassword());
            user.setPassword(encodedPassword);
            //user.setRole(((Role.USER)));
            userRepository.save(user);
            return"Registration successful";
        }
    }

    public List<User> getUser(String email) {
        Optional<User> user=userRepository.findByEmail(email);
        return userRepository.findAll();
    }

    public String getId(String userName) {
        User user= userRepository.findByName(userName).get();
        return user.getId();
    }
}
