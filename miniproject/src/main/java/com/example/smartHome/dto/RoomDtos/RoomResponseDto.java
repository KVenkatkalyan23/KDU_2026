package com.example.smartHome.dto.RoomDtos;

import com.example.smartHome.constant.ROOM_TYPE;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RoomResponseDto {
    private UUID roomId;

    private String roomName;

    private ROOM_TYPE roomType;

    private LocalDateTime createdAt;

    private LocalDateTime modifiedAt;
}
