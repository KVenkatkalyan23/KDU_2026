package com.example.smartHome.dto.DeviceDtos;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddDeviceToHouseRequestDto {

    @NotNull(message = "deviceId must not be empty")
    private UUID deviceId;

    @NotNull(message = "houseId must not be empty")
    private UUID houseId;

}
