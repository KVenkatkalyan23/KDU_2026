package com.example.smartHome.dto.DeviceDtos;


import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddDeviceToRoomRequestDto {

    @NotNull(message = "deviceId must not be empty")
    private UUID deviceId;

    @NotNull(message = "roomId must not be empty")
    private UUID roomId;

}
