package com.jwtAuth.demo.controller;


import com.jwtAuth.demo.dto.UserRequestDto;
import com.jwtAuth.demo.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@Valid @RequestBody UserRequestDto userRequestDto){
        return userService.signup(userRequestDto);
    }

    @PutMapping()
    public ResponseEntity<?> addRoleAdmin(@RequestBody String username){
        return userService.addRoleAdmin(username);
    }
}
