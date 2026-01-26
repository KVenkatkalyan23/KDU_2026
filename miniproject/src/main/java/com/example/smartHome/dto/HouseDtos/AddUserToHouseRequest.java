package com.example.smartHome.dto.HouseDtos;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddUserToHouseRequest {
    @NotBlank(message = "Username is required")
    private String username;

    private UUID houseId;
}
