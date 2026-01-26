package com.example.smartHome.service;


import com.example.smartHome.dto.UserDtos.LoginRequestDto;
import com.example.smartHome.dto.UserDtos.SignupRequestDto;
import com.example.smartHome.entity.User;
import com.example.smartHome.exception.UserAlreadyExistsException;
import com.example.smartHome.repository.UserRepository;
import com.example.smartHome.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public UserService(JwtUtil jwtUtil,AuthenticationManager authenticationManager,UserRepository userRepository,PasswordEncoder passwordEncoder){
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
    }

    public String login(LoginRequestDto loginRequestDto) {
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequestDto.getUsername(),
                        loginRequestDto.getPassword()
                )
        );
        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        String token = jwtUtil.generateToken(userDetails);
        log.info("successful login by {}", loginRequestDto.getUsername());
        return token;
    }

    public String signup(SignupRequestDto signupRequestDto) {
        boolean isUserExists = userRepository.existsById(signupRequestDto.getUsername());
        if(isUserExists){
            throw new UserAlreadyExistsException("User already exists with username : " + signupRequestDto.getUsername());
        }

        User user = new User();
        user.setUsername(signupRequestDto.getUsername());
        user.setPassword(passwordEncoder.encode(signupRequestDto.getPassword()));
        user.setName(signupRequestDto.getName());

        userRepository.save(user);
        return "User Created with username " + signupRequestDto.getUsername();
    }

}
