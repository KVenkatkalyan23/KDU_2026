package com.example.smartHome.dto.DeviceDtos;


import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DeviceRequestDto {

    @Pattern(
            regexp = "^[0-9A-Fa-f]{6}$",
            message = "kickstonId must be a 6-digit hexadecimal value"
    )
    @NotBlank(message = "Kickston Id is required!")
    private String kickstonId;

    @Column(name = "device_username", nullable = false, length = 100)
    @NotBlank(message = "Device Username is required!")
    private String deviceUsername;

    @Column(name = "device_password", nullable = false)
    @NotBlank(message = "Device Password is required!")
    private String devicePassword;

    @NotBlank(message = "Device name is required!")
    private String deviceName;

    @NotNull(message = "houseId must not be empty")
    private UUID houseId;


}
