package com.example.smartHome.dto.HouseDtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HouseRequestDto {

    @NotBlank(message = "House name should be empty!")
    private String houseName;

    @NotBlank(message = "Address should not be empty!")
    private String address;

    @Size(max = 100, message = "City must not exceed 100 characters")
    private String city;

    @Size(max = 100, message = "State must not exceed 100 characters")
    private String state;

    @Size(max = 6, message = "Postal Code must not exceed 6 characters")
    private String postalCode;

}
