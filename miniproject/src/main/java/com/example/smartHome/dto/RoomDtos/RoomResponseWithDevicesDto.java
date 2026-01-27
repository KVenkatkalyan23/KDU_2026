package com.example.smartHome.dto.RoomDtos;

import com.example.smartHome.constant.ROOM_TYPE;
import com.example.smartHome.dto.DeviceDtos.DeviceResponseDto;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RoomResponseWithDevicesDto {

    private UUID roomId;

    private String roomName;

    private ROOM_TYPE roomType;

    private List<DeviceResponseDto> device;

    private LocalDateTime createdAt;

    private LocalDateTime modifiedAt;

}
