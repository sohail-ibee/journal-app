package com.heysohail.journalApp.service;

import com.heysohail.journalApp.entity.UserEntity;
import com.heysohail.journalApp.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public ResponseEntity<?> createUser(UserEntity user) {
        try {
            UserEntity savedUser = userRepository.save(user);
            return new ResponseEntity<>(savedUser.getId(), HttpStatus.CREATED);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<List<UserEntity>> getAllUsers() {
        try {
            List<UserEntity> users = userRepository.findAll();

            return new ResponseEntity<>(users, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<UserEntity> getAUser(ObjectId id) {
        try {
            Optional<UserEntity> user = userRepository.findById(id);

            if (user.isPresent()) {
                return new ResponseEntity<>(user.get(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<UserEntity> updateUser(ObjectId id, UserEntity user) {
        try {
            Optional<UserEntity> oldUser = userRepository.findById(id);

            if (oldUser.isPresent()) {
                oldUser.get().setEmail(!user.getEmail().isEmpty() ? user.getEmail() : oldUser.get().getEmail());
                oldUser.get().setPassword(!user.getPassword().isEmpty() ? user.getPassword() : oldUser.get().getPassword());
                userRepository.save(oldUser.get());
                return new ResponseEntity<>(oldUser.get(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<?> deleteUser(ObjectId id) {
        try {
            userRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
