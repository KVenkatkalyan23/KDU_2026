package com.example.smartHome.dto.DeviceDtos;

import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;



@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DeviceResponseDto {

    private UUID deviceId;

    private String kickstonId;

    private String deviceName;

    private UUID room;

    private UUID house;

    private String ownedBy;

    private LocalDateTime createdAt;

    private LocalDateTime modifiedAt;

}
