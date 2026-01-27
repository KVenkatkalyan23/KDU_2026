package com.example.smartHome.util;

import com.example.smartHome.dto.DeviceDtos.DeviceRequestDto;
import com.example.smartHome.dto.DeviceDtos.DeviceResponseDto;
import com.example.smartHome.entity.Device;

import java.time.LocalDateTime;

public class DeviceUtils {

    public static DeviceResponseDto mapDeviceToDeviceResponseDto(Device device){
        return DeviceResponseDto
                .builder()
                .deviceId(device.getDeviceId())
                .deviceName(device.getDeviceName())
                .kickstonId(device.getKickstonId())
                .room(device.getRoom() == null ? null : device.getRoom().getRoomId())
                .house(device.getHouse() == null ? null :device.getHouse().getHouseId())
                .modifiedAt(device.getModifiedAt())
                .createdAt(device.getCreatedAt())
                .ownedBy(device.getOwnedBy())
                .build();
    }

    public static Device mapDeviceRequestDtoToDevice(DeviceRequestDto deviceRequestDto){
        return Device
                .builder()
                .deviceName(deviceRequestDto.getDeviceName())
                .modifiedAt(LocalDateTime.now())
                .kickstonId(deviceRequestDto.getKickstonId())
                .build();
    }



}
