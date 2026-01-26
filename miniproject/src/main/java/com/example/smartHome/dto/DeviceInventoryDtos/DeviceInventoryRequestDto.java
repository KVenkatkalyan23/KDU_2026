package com.example.smartHome.dto.DeviceInventoryDtos;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DeviceInventoryRequestDto {

    @Pattern(
            regexp = "^[0-9A-Fa-f]{6}$",
            message = "kickstonId must be a 6-digit hexadecimal value"
    )
    private String kickstonId;

    @NotBlank(message = "Device Username is required!")
    private String deviceUsername;

    @NotBlank(message = "Device Password is required!")
    private String devicePassword;

    @NotBlank(message = "manufacture Date and Time is required!")
    private LocalDateTime manufactureDateTime;

    @NotBlank(message = "manufactureFactory Place is required!")
    private String manufactureFactoryPlace;

}
