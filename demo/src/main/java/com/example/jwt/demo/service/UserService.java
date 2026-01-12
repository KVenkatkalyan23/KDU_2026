package com.example.jwt.demo.service;


import com.example.jwt.demo.dto.UserRequestDto;
import com.example.jwt.demo.entity.User;
import com.example.jwt.demo.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    public UserService(JwtUtil jwtUtil,AuthenticationManager authenticationManager,UserRepository userRepository,PasswordEncoder passwordEncoder){
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
    }

    public ResponseEntity<?> login(UserRequestDto userRequestDto) {
        if(userRequestDto.getUsername().isEmpty() || userRequestDto.getPassword().isEmpty()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Username and password should not be empty");
        }
        try {
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            userRequestDto.getUsername(),
                            userRequestDto.getPassword()
                    )
            );

            UserDetails userDetails = (UserDetails) auth.getPrincipal();
            String token = jwtUtil.generateToken(userDetails);

            logger.info("successful login by {}",userRequestDto.getUsername());

            return ResponseEntity.ok(Map.of("token", token));
        }catch (Exception e){
            System.out.println(e.getMessage());
            throw e;
        }
    }

    public ResponseEntity<?> addUser(UserRequestDto userRequestDto) {
        if(userRequestDto.getUsername().isEmpty() || userRequestDto.getPassword().isEmpty()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Username and password should not be empty");
        }

        // check if user already exists
        Optional<User> isUserExists = userRepository.findByUsername(userRequestDto.getUsername());
        if(isUserExists.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User Already Exits with Username :" + userRequestDto.getUsername());
        }
        User user = new User();
        user.setUsername(userRequestDto.getUsername());
        user.setPassword(passwordEncoder.encode(userRequestDto.getPassword()));
        user.setRoles(List.of("ROLE_DRIVER"));

        User savedUser = userRepository.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body("User created successfully");
    }

    public ResponseEntity<?> addRoleAdmin(String username) {
            if(username.isEmpty()){
               return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Username required");
            }
            Optional<User> isUserExists = userRepository.findByUsername(username);
            if(isUserExists.isEmpty()){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not Exits with Username :" + username);
            }

            Optional<User> optionalUser = userRepository.findByUsername(username); // fetch users from database
            User user = optionalUser.get();

            // Avoid duplicate role
            if (user.getRoles().contains("ROLE_MANAGER")) {
                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body("User is already an ADMIN");
            }

            List<String> updatedRoles = new ArrayList<>(user.getRoles());
            updatedRoles.add("ROLE_MANAGER");
            user.setRoles(updatedRoles);

            userRepository.save(user);

            return ResponseEntity.ok(
                    "User " + username + " promoted to MANAGER");

    }
}
