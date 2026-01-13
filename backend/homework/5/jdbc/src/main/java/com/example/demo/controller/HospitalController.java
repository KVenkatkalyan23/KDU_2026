package com.example.demo.controller;


import com.example.demo.dto.ShiftRequestDto;
import com.example.demo.dto.UserRequestDto;
import com.example.demo.repository.HospitalRepository;
import com.example.demo.service.HospitalService;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class HospitalController {

    private final HospitalService hospitalService;

    public HospitalController(HospitalService hospitalService){
        this.hospitalService = hospitalService;
    }

    @PostMapping("/user")
    public ResponseEntity<?> adduser(UserRequestDto userRequestDto){
        System.out.println(userRequestDto.getUsername());
        hospitalService.addUser(userRequestDto);
        return ResponseEntity.ok("user created successfully");
    }

    @PostMapping("/shift")
    public ResponseEntity<?> addShift(ShiftRequestDto shiftRequestDto){
        hospitalService.addShift(shiftRequestDto);
        return ResponseEntity.ok("user shift successfully");
    }

}
