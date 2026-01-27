package com.example.smartHome.controller;


import com.example.smartHome.dto.ApiResponse;
import com.example.smartHome.dto.UserDtos.LoginRequestDto;
import com.example.smartHome.dto.UserDtos.SignupRequestDto;
import com.example.smartHome.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@Valid @RequestBody SignupRequestDto signupRequestDto){
        String message = userService.signup(signupRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(message);
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<String>> login(@Valid @RequestBody LoginRequestDto loginRequestDto){
        String token = userService.login(loginRequestDto);
        return ResponseEntity.ok(new ApiResponse<>("Login successful",token));
    }


}
