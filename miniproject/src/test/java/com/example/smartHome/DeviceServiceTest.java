package com.example.smartHome;


import com.example.smartHome.dto.DeviceDtos.AddDeviceToRoomRequestDto;
import com.example.smartHome.entity.Device;
import com.example.smartHome.entity.House;
import com.example.smartHome.entity.Room;
import com.example.smartHome.repository.DeviceInventoryRepository;
import com.example.smartHome.repository.DeviceRepository;
import com.example.smartHome.service.DeviceService;
import com.example.smartHome.util.Utils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DeviceServiceTest {

    @Mock
    private DeviceRepository deviceRepository;

    @Mock
    private DeviceInventoryRepository deviceInventoryRepository;

    @Mock
    private Utils utils;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private DeviceService deviceService;


    @Test
    void shouldMoveDeviceToAnotherRoomWithinSameHouse() {

        UUID deviceId = UUID.randomUUID();
        UUID houseId = UUID.randomUUID();
        UUID oldRoomId = UUID.randomUUID();
        UUID newRoomId = UUID.randomUUID();

        House house = new House();
        house.setHouseId(houseId);

        Device device = new Device();
        device.setDeviceId(deviceId);
        device.setDeviceName("Smart Light");
        device.setHouse(house);

        Room oldRoom = new Room();
        oldRoom.setRoomId(oldRoomId);
        oldRoom.setHouse(house);
        oldRoom.setDevices(new HashSet<>(List.of(device)));
        oldRoom.setRoomName("Bedroom");

        Room newRoom = new Room();
        newRoom.setRoomId(newRoomId);
        newRoom.setHouse(house);
        newRoom.setDevices(new HashSet<>());
        newRoom.setRoomName("Hall");
        device.setRoom(oldRoom);

        when(utils.getDeviceById(deviceId)).thenReturn(device);
        when(utils.getRoomById(oldRoomId)).thenReturn(oldRoom);
        when(deviceRepository.save(device)).thenReturn(device);

        // Detach
        deviceService.detachDeviceFromTheRoom(deviceId);
        assertNull(device.getRoom());
        assertEquals(0,oldRoom.getDevices().size());

        // Attach
        AddDeviceToRoomRequestDto requestDto = new AddDeviceToRoomRequestDto();
        requestDto.setDeviceId(deviceId);
        requestDto.setRoomId(newRoomId);

        when(utils.getRoomById(newRoomId)).thenReturn(newRoom);
        when(deviceRepository.save(device)).thenReturn(device);

        String response = deviceService.addDeviceToRoom(requestDto);

        assertNotNull(device.getRoom());
        assertEquals(device.getRoom().getRoomId(),newRoomId);
        assertEquals(1,newRoom.getDevices().size());

    }



}
