package com.example.smartHome.dto.DeviceDtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class GetDevicesByOwnerResponseDto {

    private String username;

    private List<DeviceResponseDto> deviceResponseDto;

}
