package com.taskmanager.taskmanager.controller;

import com.taskmanager.taskmanager.domain.User;
import com.taskmanager.taskmanager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping("/add")
    public ResponseEntity<String> addUser(@RequestBody User user){
        return ResponseEntity.ok(userService.addUser(user));
    }

    @GetMapping("{email}")
    public ResponseEntity<List<User>> getUser(@PathVariable(value = "email") String email){
        return ResponseEntity.ok(userService.getUser(email));
    }

}
