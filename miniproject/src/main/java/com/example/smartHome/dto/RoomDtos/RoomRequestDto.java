package com.example.smartHome.dto.RoomDtos;

import com.example.smartHome.constant.ROOM_TYPE;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RoomRequestDto {

    @NotBlank(message = "Room name is required!")
    private String roomName;

    private ROOM_TYPE roomType;

    @NotNull(message = "houseId must not be empty")
    private UUID houseId;

}
