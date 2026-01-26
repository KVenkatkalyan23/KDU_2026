package com.example.smartHome.util;

import com.example.smartHome.entity.Device;
import com.example.smartHome.entity.House;
import com.example.smartHome.entity.Room;
import com.example.smartHome.entity.User;
import com.example.smartHome.exception.DeviceNotFoundException;
import com.example.smartHome.exception.HouseNotFoundException;
import com.example.smartHome.exception.UserNotFoundException;
import com.example.smartHome.repository.DeviceRepository;
import com.example.smartHome.repository.HouseRepository;
import com.example.smartHome.repository.RoomRepository;
import com.example.smartHome.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.UUID;

@AllArgsConstructor
@Component
public class Utils {

    private HouseRepository houseRepository;
    private UserRepository userRepository;
    private DeviceRepository deviceRepository;
    private RoomRepository roomRepository;


    public  String getCurrentUsername(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }

    public Room getRoomById(UUID roomId){
        return roomRepository.findById(roomId)
                .orElseThrow(() -> new DeviceNotFoundException("Room not found with id : " + roomId));
    }

    public House getHouseById(UUID houseId){
        return houseRepository.findById(houseId)
                .orElseThrow(() -> new HouseNotFoundException("House not found with id : " + houseId));
    }

    public User getUserById(String username){
        return userRepository.findById(username)
                .orElseThrow(() -> new UserNotFoundException("User not found with username : " + username));
    }


    public  Device getDeviceById(UUID deviceId){
        return deviceRepository.findById(deviceId)
                .orElseThrow(() -> new DeviceNotFoundException("Device not found with Id " + deviceId));
    }



}
