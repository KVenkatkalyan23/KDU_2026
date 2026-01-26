package com.example.smartHome.service;

import com.example.smartHome.dto.RoomDtos.RoomRequestDto;
import com.example.smartHome.dto.RoomDtos.RoomResponseDto;
import com.example.smartHome.dto.RoomDtos.RoomResponseWithDevicesDto;
import com.example.smartHome.entity.House;
import com.example.smartHome.entity.Room;
import com.example.smartHome.exception.AccessDeniedException;
import com.example.smartHome.repository.DeviceRepository;
import com.example.smartHome.repository.RoomRepository;
import com.example.smartHome.util.RoomUtils;
import com.example.smartHome.util.Utils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class RoomService {

    private RoomRepository roomRepository;
    private DeviceRepository deviceRepository;
    private Utils utils;

    public String addRoom(RoomRequestDto roomRequestDto) {
        String currentUsername = utils.getCurrentUsername();
        House house = utils.getHouseById(roomRequestDto.getHouseId());
        if(!house.getAdmin().equals(currentUsername)){
            throw new AccessDeniedException("User should be Admin to add room to house");
        }
        Room room = RoomUtils.mapRoomRequestDtoToRoom(roomRequestDto);
        room.setHouse(house);
        roomRepository.save(room);
        return "Room successfully added to house : " + house.getHouseName();
    }

    @Transactional(readOnly = true)
    public Set<RoomResponseWithDevicesDto> getAllRoomsWithDevicesByHouseId(UUID houseId) {
        utils.getHouseById(houseId);
        Set<Room> rooms = roomRepository.findByHouse_HouseIdAndDeletedAtIsNull(houseId);
        return rooms.stream()
                .map(RoomUtils::mapRoomToRoomResponseWithDevicesDto)
                .collect(Collectors.toSet());
    }

    @Transactional
    public String softDeleteRoom(UUID roomId) {
        Room room = utils.getRoomById(roomId);
        House house = utils.getHouseById(room.getHouse().getHouseId());
        String currentUser = utils.getCurrentUsername();
        if (!currentUser.equals(house.getAdmin())) {
            throw new AccessDeniedException("Only house admin can attach devices");
        }
        house.getRooms().remove(room);
        roomRepository.softDeleteByRoomId(roomId);
        deviceRepository.detachDevicesFromRoom(roomId);
        return room.getRoomName() + "Successfully deleted";
    }

    public Set<RoomResponseDto> getAllRoomsByHouseId(UUID houseId) {
        utils.getHouseById(houseId);
        Set<Room> rooms = roomRepository.findByHouse_HouseIdAndDeletedAtIsNull(houseId);
        return rooms.stream()
                .map(RoomUtils::mapRoomToRoomResponseWithoutDevicesDto)
                .collect(Collectors.toSet());
    }
}
