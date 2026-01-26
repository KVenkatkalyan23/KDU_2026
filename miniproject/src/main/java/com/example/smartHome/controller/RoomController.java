package com.example.smartHome.controller;

import com.example.smartHome.dto.ApiResponse;
import com.example.smartHome.dto.RoomDtos.RoomRequestDto;
import com.example.smartHome.dto.RoomDtos.RoomResponseDto;
import com.example.smartHome.dto.RoomDtos.RoomResponseWithDevicesDto;
import com.example.smartHome.service.RoomService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/room")
@AllArgsConstructor
public class RoomController {

    private RoomService roomservice;

    @PostMapping()
    public ResponseEntity<String> addRoom(@RequestBody @Valid RoomRequestDto roomRequestDto){
        String message = roomservice.addRoom(roomRequestDto);
        return ResponseEntity.ok(message);
    }

    @GetMapping("/{houseId}")
    public ResponseEntity<ApiResponse<Set<RoomResponseDto>>> getAllRoomsByHouseId(@PathVariable UUID houseId){
        Set<RoomResponseDto> rooms = roomservice.getAllRoomsByHouseId(houseId);
        return ResponseEntity.ok(new ApiResponse<>("List of rooms in house with id " + houseId,rooms));
    }

    @GetMapping("/with-devices/{houseId}")
    public ResponseEntity<ApiResponse<Set<RoomResponseWithDevicesDto>>> getAllRoomsWithDevicesByHouseId(@PathVariable UUID houseId){
        Set<RoomResponseWithDevicesDto> rooms = roomservice.getAllRoomsWithDevicesByHouseId(houseId);
        return ResponseEntity.ok(new ApiResponse<>("Rooms with devices in house with Id : " + houseId,rooms));
    }

    @DeleteMapping("/{roomId}")
    public ResponseEntity<String> softDeleteRoom(@PathVariable UUID roomId){
        String message = roomservice.softDeleteRoom(roomId);
        return ResponseEntity.ok(message);
    }
}
