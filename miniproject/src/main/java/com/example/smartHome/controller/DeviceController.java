package com.example.smartHome.controller;

import com.example.smartHome.dto.ApiResponse;
import com.example.smartHome.dto.DeviceDtos.AddDeviceToHouseRequestDto;
import com.example.smartHome.dto.DeviceDtos.AddDeviceToRoomRequestDto;
import com.example.smartHome.dto.DeviceDtos.DeviceRequestDto;
import com.example.smartHome.dto.DeviceDtos.DeviceResponseDto;
import com.example.smartHome.service.DeviceService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/device")
@AllArgsConstructor
public class DeviceController {

    private DeviceService deviceService;

    @GetMapping("/by-owner")
    public ResponseEntity<List<DeviceResponseDto>> getDevicesByOwnerNotAttachedToHouseOrRoom(){
        List<DeviceResponseDto> deviceResponseDto = deviceService.getDevicesByOwnerNotAttachedToHouseOrRoom();
        return ResponseEntity.ok(deviceResponseDto);
    }

    @GetMapping("/by-house/{houseId}")
    public ResponseEntity<ApiResponse<List<DeviceResponseDto>>> getDevicesByHomeId(@PathVariable UUID houseId){
        List<DeviceResponseDto> devices = deviceService.getDevicesByHomeID(houseId);
        return ResponseEntity.ok(new ApiResponse<>("List of devices in house with Id : " + houseId,devices));
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<DeviceResponseDto>> registerDevice(@RequestBody DeviceRequestDto deviceRequestDto){
        DeviceResponseDto deviceResponseDto = deviceService.registerDevice(deviceRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<>("Device successfully registered with name : " + deviceRequestDto.getDeviceName(),deviceResponseDto));
    }

    @PostMapping("/addtohouse")
    public ResponseEntity<String> addDeviceToHouse(@RequestBody AddDeviceToHouseRequestDto addDeviceToHouseRequestDto){
        String message = deviceService.addDeviceToHouse(addDeviceToHouseRequestDto);
        return ResponseEntity.ok(message);
    }

    @PostMapping("/detachfromhouse")
    public ResponseEntity<String> detachDeviceFromHouse(@RequestBody UUID deviceId){
        String message = deviceService.detachDeviceFromHouse(deviceId);
        return ResponseEntity.ok(message);
    }

    @PostMapping("/addtoroom")
    public ResponseEntity<String> addDeviceToRoom(@RequestBody @Valid AddDeviceToRoomRequestDto addDeviceToRoomRequestDto){
        String message = deviceService.addDeviceToRoom(addDeviceToRoomRequestDto);
        return ResponseEntity.ok(message);
    }

    @PostMapping("/detachfromroom")
    public ResponseEntity<String> detachDeviceFromTheRoom(@RequestBody UUID deviceId){
        String message = deviceService.detachDeviceFromTheRoom(deviceId);
        return ResponseEntity.ok(message);
    }
}
