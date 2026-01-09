package com.jwtAuth.demo.service;


import com.jwtAuth.demo.dto.UserRequestDto;
import com.jwtAuth.demo.entity.User;
import com.jwtAuth.demo.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public ResponseEntity<?> signup(UserRequestDto userRequestDto) {
        Optional<User> isUserExists = userRepository.findByUsername(userRequestDto.getUsername());
        if(isUserExists.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User Already Exits with Username :" + userRequestDto.getUsername());
        }

        User user = new User();
        user.setUsername(userRequestDto.getUsername());
        user.setPassword(passwordEncoder.encode(userRequestDto.getPassword()));
        user.setRoles(List.of("ROLE_BASIC"));

        User savedUser = userRepository.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body("User created successfully");
    }

    public ResponseEntity<?> addRoleAdmin(String username){
        Optional<User> isUserExists = userRepository.findByUsername(username);
        if(isUserExists.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not Exits with Username :" + username);
        }

        Optional<User> optionalUser = userRepository.findByUsername(username);
        User user = optionalUser.get();

        // Avoid duplicate role
        if (user.getRoles().contains("ROLE_ADMIN")) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("User is already an ADMIN");
        }

        List<String> updatedRoles = new ArrayList<>(user.getRoles());
        updatedRoles.add("ROLE_ADMIN");
        user.setRoles(updatedRoles);

        userRepository.save(user);

        return ResponseEntity.ok(
                "User " + username + " promoted to ADMIN");
    }
}
