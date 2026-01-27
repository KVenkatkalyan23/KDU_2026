package com.example.smartHome.service;

import com.example.smartHome.dto.DeviceInventoryDtos.DeviceInventoryRequestDto;
import com.example.smartHome.entity.DeviceInventory;
import com.example.smartHome.repository.DeviceInventoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DeviceInventoryService {

    private DeviceInventoryRepository deviceInventoryRepository;
    private PasswordEncoder passwordEncoder;

    public DeviceInventory addDevice(DeviceInventoryRequestDto deviceInventoryRequestDto) {
        DeviceInventory deviceInventory = mapDeviceInventoryRequestDtoToDeviceInventory(deviceInventoryRequestDto);
        return deviceInventoryRepository.save(deviceInventory);
    }

    private DeviceInventory mapDeviceInventoryRequestDtoToDeviceInventory(DeviceInventoryRequestDto deviceInventoryRequestDto){
        return DeviceInventory
                .builder()
                .kickstonId(deviceInventoryRequestDto.getKickstonId())
                .deviceUsername(deviceInventoryRequestDto.getDeviceUsername())
                .devicePassword(passwordEncoder.encode(deviceInventoryRequestDto.getDevicePassword()))
                .manufactureDateTime(deviceInventoryRequestDto.getManufactureDateTime())
                .manufactureFactoryPlace(deviceInventoryRequestDto.getManufactureFactoryPlace())
                .build();
    }
}
