package com.heysohail.journalApp.controllers;


import com.heysohail.journalApp.entity.UserEntity;
import com.heysohail.journalApp.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/create")
    public ResponseEntity<?> createUser(@RequestBody UserEntity user) {
        return userService.createUser(user);
    }

    @GetMapping("/all")
    public ResponseEntity<List<UserEntity>> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/userId/{userId}")
    public ResponseEntity<UserEntity> getAUser(@PathVariable ObjectId userId) {
        return userService.getAUser(userId);
    }

    @PutMapping("/edit/userId/{userId}")
    public ResponseEntity<UserEntity> editUser(@PathVariable ObjectId userId, @RequestBody UserEntity user) {
        return userService.updateUser(userId, user);
    }

    @DeleteMapping("/delete/userId/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable ObjectId userId) {
        return userService.deleteUser(userId);
    }
}
