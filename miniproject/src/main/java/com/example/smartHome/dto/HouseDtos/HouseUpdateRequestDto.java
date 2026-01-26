package com.example.smartHome.dto.HouseDtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HouseUpdateRequestDto {

    @NotNull(message = "houseId must not be empty")
    private UUID houseId;

    @NotBlank(message = "House name is required!")
    private String houseName;

    private String address;

    private String city;

    private String state;

    private String postalCode;
}
