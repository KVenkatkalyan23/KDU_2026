package com.example.smartHome.controller;

import com.example.smartHome.dto.ApiResponse;
import com.example.smartHome.dto.DeviceInventoryDtos.DeviceInventoryRequestDto;
import com.example.smartHome.entity.DeviceInventory;
import com.example.smartHome.service.DeviceInventoryService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/device-inventory")
@AllArgsConstructor
public class DeviceInventoryController {

    private DeviceInventoryService deviceInventoryService;

    @PostMapping()
    public ResponseEntity<ApiResponse<DeviceInventory>> addDevice(@RequestBody @Valid DeviceInventoryRequestDto deviceInventoryRequestDto){
        DeviceInventory deviceInventory = deviceInventoryService.addDevice(deviceInventoryRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<>("Device successfully added to inventory",deviceInventory));
    }
}
