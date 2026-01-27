package com.example.smartHome.dto.HouseDtos;

import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HouseResponseDto {

    private UUID houseId;

    private String houseName;

    private String address;

    private String city;

    private String state;

    private String postalCode;

    private String admin;

    private LocalDateTime createdAt;

    private LocalDateTime modifiedAt;

}
