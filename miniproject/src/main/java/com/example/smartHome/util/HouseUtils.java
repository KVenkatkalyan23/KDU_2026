package com.example.smartHome.util;

import com.example.smartHome.dto.HouseDtos.HouseRequestDto;
import com.example.smartHome.dto.HouseDtos.HouseResponseDto;
import com.example.smartHome.entity.House;

public class HouseUtils {

    public static HouseResponseDto mapHouseToHouseResponseDto(House house){
        return HouseResponseDto
                .builder()
                .houseId(house.getHouseId())
                .houseName(house.getHouseName())
                .address(house.getAddress())
                .city(house.getCity())
                .state(house.getState())
                .postalCode(house.getPostalCode())
                .createdAt(house.getCreatedAt())
                .modifiedAt(house.getModifiedAt())
                .admin(house.getAdmin())
                .build();
    }

    public static House mapHouseRequestDtoToHouse(HouseRequestDto houseRequestDto){
        return House
                .builder()
                .houseName(houseRequestDto.getHouseName())
                .address(houseRequestDto.getAddress())
                .postalCode(houseRequestDto.getPostalCode())
                .city(houseRequestDto.getCity())
                .state(houseRequestDto.getState())
                .build();
    }
}
