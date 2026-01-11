package com.example.jwt.demo.controller;


import com.example.jwt.demo.dto.UserRequestDto;
import com.example.jwt.demo.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> addUser(@Valid @RequestBody UserRequestDto userRequestDto){
        return userService.addUser(userRequestDto);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody UserRequestDto userRequestDto){
        return userService.login(userRequestDto);
    }

    @PutMapping("/add-role")
    public ResponseEntity<?> addRoleAdmin(@RequestBody String username){
        return userService.addRoleAdmin(username);
    }

    @GetMapping()
    @PreAuthorize("hasRole('ADMIN')")
    public String getData(){
        return "hello";
    }
}
