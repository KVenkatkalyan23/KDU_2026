package com.example.smartHome.controller;

import com.example.smartHome.dto.ApiResponse;
import com.example.smartHome.dto.HouseDtos.*;
import com.example.smartHome.service.HouseService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/house")
@AllArgsConstructor
public class HouseController {

    private HouseService houseService;

    @PostMapping()
    public ResponseEntity<ApiResponse<HouseResponseDto>> addHouse(@RequestBody @Valid HouseRequestDto houseRequestDto){
        HouseResponseDto houseResponseDto = houseService.addHouse(houseRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<>("House Added successfully",houseResponseDto));
    }

    @GetMapping("/by-admin")
    public ResponseEntity<ApiResponse<List<HouseResponseDto>>> getAllHousesByAdmin(){
        List<HouseResponseDto> houses = houseService.getAllHousesByAdmin();
        return ResponseEntity.ok(new ApiResponse<>("List of all Houses that you are admin of",houses));
    }

    @GetMapping()
    public ResponseEntity<ApiResponse<List<HouseResponseDto>>> getAllHouses(){
        List<HouseResponseDto> houses = houseService.getAllHouses();
        return ResponseEntity.ok(new ApiResponse<>("List of all Houses",houses));
    }

    @PostMapping("/add-user")
    public ResponseEntity<String> addUserToHouse(@RequestBody @Valid AddUserToHouseRequest addUserToHouseRequest){
        String message = houseService.addUserToHouse(addUserToHouseRequest);
        return ResponseEntity.ok(message);
    }

    @DeleteMapping("/{houseId}")
    public ResponseEntity<String> softDeleteHouse(@PathVariable UUID houseId){
        String message = houseService.softDeleteHouse(houseId);
        return ResponseEntity.ok(message);
    }

    @PutMapping("/transfer-ownership")
    public ResponseEntity<String> transferOwnership(@RequestBody @Valid TransferOwnerShipRequest transferOwnerShipRequest){
        String message = houseService.transferOwnership(transferOwnerShipRequest.getUsername(),transferOwnerShipRequest.getHouseId());
        return ResponseEntity.ok(message);
    }

    @PutMapping()
    public ResponseEntity<ApiResponse<HouseResponseDto>> updateHouse(@RequestBody @Valid HouseUpdateRequestDto houseUpdateRequestDto){
        HouseResponseDto houseResponseDto = houseService.updateHouse(houseUpdateRequestDto);
        return ResponseEntity.ok(new ApiResponse<>("House Details Updated successfully",houseResponseDto));
    }
}
