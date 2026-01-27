package com.example.smartHome.util;

import com.example.smartHome.dto.RoomDtos.RoomRequestDto;
import com.example.smartHome.dto.RoomDtos.RoomResponseDto;
import com.example.smartHome.dto.RoomDtos.RoomResponseWithDevicesDto;
import com.example.smartHome.entity.Room;

import java.time.LocalDateTime;

public class RoomUtils {
    public static RoomResponseWithDevicesDto mapRoomToRoomResponseWithDevicesDto(Room room){
        RoomResponseWithDevicesDto roomResponseDto = new RoomResponseWithDevicesDto();
        roomResponseDto.setRoomName(room.getRoomName());
        roomResponseDto.setRoomType(room.getRoomType());
        roomResponseDto.setRoomId(room.getRoomId());
        roomResponseDto.setCreatedAt(room.getCreatedAt());
        roomResponseDto.setModifiedAt(room.getModifiedAt());
        roomResponseDto.setDevice(room.getDevices().stream().map(DeviceUtils::mapDeviceToDeviceResponseDto).toList());
        return roomResponseDto;
    }

    public static RoomResponseDto mapRoomToRoomResponseWithoutDevicesDto(Room room){
        RoomResponseDto roomResponseDto = new RoomResponseDto();
        roomResponseDto.setRoomName(room.getRoomName());
        roomResponseDto.setRoomType(room.getRoomType());
        roomResponseDto.setRoomId(room.getRoomId());
        roomResponseDto.setCreatedAt(room.getCreatedAt());
        roomResponseDto.setModifiedAt(room.getModifiedAt());
        return roomResponseDto;
    }


    public static Room mapRoomRequestDtoToRoom(RoomRequestDto roomRequestDto){
        return Room
                .builder()
                .roomType(roomRequestDto.getRoomType())
                .roomName(roomRequestDto.getRoomName())
                .modifiedAt(LocalDateTime.now())
                .build();
    }
}
