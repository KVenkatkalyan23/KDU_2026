package com.example.demo.controller;


import com.example.demo.dto.OnboardRequestDto;
import com.example.demo.dto.ShiftTypeDto;
import com.example.demo.dto.UserRequestDto;
import com.example.demo.dto.UserResponseDto;
import com.example.demo.service.HospitalService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class HospitalController {

    private final HospitalService hospitalService;

    public HospitalController(HospitalService hospitalService){
        this.hospitalService = hospitalService;
    }

    @PostMapping("/users")
    public ResponseEntity<?> saveUser(@RequestBody UserRequestDto userRequestDto){
        hospitalService.saveUser(userRequestDto);
        return ResponseEntity.ok("user created successfully");
    }

    @GetMapping("/tenant/{id}")
    public ResponseEntity<?> getUsersByTenantId(@PathVariable int id){
        List<UserResponseDto> userList = hospitalService.getUsersByTenantId( id);
        if(userList.isEmpty()){
            return ResponseEntity.ok("No users with tenant id : " + id);
        }
        return ResponseEntity.ok(userList);
    }

    @PostMapping("/shift-type")
    public ResponseEntity<?> addShiftType(@RequestBody ShiftTypeDto shiftTypeDto){
        hospitalService.addShiftType(shiftTypeDto);
        return ResponseEntity.ok("shift type created successfully");
    }

    @PostMapping("/user-shiftType")
    public ResponseEntity<?> addUserAndShiftType(@RequestBody OnboardRequestDto onboardRequestDto){
        hospitalService.addUserAndShiftType(onboardRequestDto);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("something went wrong");
    }

    @GetMapping("/users")
    public ResponseEntity<?> getUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "asc") String sort
    )
    {
        List<UserResponseDto> users= hospitalService.getUsers(page,size,sort);
        if(users.isEmpty()){
            return ResponseEntity.ok("No users");
        }
        return ResponseEntity.ok(users);
    }

    @PutMapping("/edit-username")
    public ResponseEntity<?> editUsername(@RequestBody int id, @RequestBody String username){
        hospitalService.editUsername(id,username);
        return ResponseEntity.ok("username changed successfully to " + username);
    }


}
