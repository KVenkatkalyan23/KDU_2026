package com.example.smartHome.service;

import com.example.smartHome.dto.DeviceDtos.AddDeviceToHouseRequestDto;
import com.example.smartHome.dto.DeviceDtos.AddDeviceToRoomRequestDto;
import com.example.smartHome.dto.DeviceDtos.DeviceRequestDto;
import com.example.smartHome.dto.DeviceDtos.DeviceResponseDto;
import com.example.smartHome.entity.Device;
import com.example.smartHome.entity.DeviceInventory;
import com.example.smartHome.entity.House;
import com.example.smartHome.entity.Room;
import com.example.smartHome.exception.AccessDeniedException;
import com.example.smartHome.exception.DeviceRegisteredException;
import com.example.smartHome.exception.InvalidAttachmentException;
import com.example.smartHome.exception.InvalidDeviceCredentialsException;
import com.example.smartHome.repository.DeviceInventoryRepository;
import com.example.smartHome.repository.DeviceRepository;
import com.example.smartHome.repository.HouseRepository;
import com.example.smartHome.repository.RoomRepository;
import com.example.smartHome.util.DeviceUtils;
import com.example.smartHome.util.Utils;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.example.smartHome.util.DeviceUtils.mapDeviceRequestDtoToDevice;
import static com.example.smartHome.util.DeviceUtils.mapDeviceToDeviceResponseDto;

@Service
@AllArgsConstructor
public class DeviceService {

    private DeviceRepository deviceRepository;
    private DeviceInventoryRepository deviceInventoryRepository;
    private Utils utils;
    private PasswordEncoder passwordEncoder;

    @Transactional
    public DeviceResponseDto registerDevice(DeviceRequestDto deviceRequestDto) {

        DeviceInventory deviceInventory = deviceInventoryRepository
                .findById(deviceRequestDto.getKickstonId())
                .orElseThrow(() ->
                        new InvalidDeviceCredentialsException(
                                "Invalid Device KickstonId " + deviceRequestDto.getKickstonId()
                        )
                );

        if(deviceInventory.getIsRegistered()){
            throw new DeviceRegisteredException("Device had been registered already");
        }

        if (!passwordEncoder.matches(
                deviceRequestDto.getDevicePassword(),
                deviceInventory.getDevicePassword()
        )) {
            throw new InvalidDeviceCredentialsException("Invalid Device Password");
        }

        House house = null;

        if (deviceRequestDto.getHouseId() != null) {
            house = utils.getHouseById(deviceRequestDto.getHouseId());

            if (!utils.getCurrentUsername().equals(house.getAdmin())) {
                throw new AccessDeniedException(
                        "Only the house admin can register this device"
                );
            }
        }

        Device device = mapDeviceRequestDtoToDevice(deviceRequestDto);
        device.setOwnedBy(utils.getCurrentUsername());
        device.setHouse(house);

        Device savedDevice = deviceRepository.save(device);

        if (house != null) {
            house.getDevices().add(savedDevice);
        }

        deviceInventory.setIsRegistered(true);
        deviceInventoryRepository.save(deviceInventory);

        return mapDeviceToDeviceResponseDto(savedDevice);
    }

    public String addDeviceToHouse(AddDeviceToHouseRequestDto addDeviceToHouseRequestDto){
        Device device = utils.getDeviceById(addDeviceToHouseRequestDto.getDeviceId());
        Optional<DeviceInventory> inventoryDevice = deviceInventoryRepository.findById(device.getKickstonId());

        if(!inventoryDevice.get().getIsRegistered()){
            throw new InvalidAttachmentException("Device must be attached to a house");
        }

        if(device.getHouse() != null){
            throw new InvalidAttachmentException("Device is Already attached to a house");
        }

        House house = utils.getHouseById(addDeviceToHouseRequestDto.getHouseId());

        String currentUser = utils.getCurrentUsername();
        if (!currentUser.equals(house.getAdmin())) {
            throw new AccessDeniedException("Only house admin can attach devices");
        }

        device.setHouse(house);
        Device savedDevice = deviceRepository.save(device);
        house.getDevices().add(savedDevice);
        return "Device " + device.getDeviceName() + " successfully added to house " + house.getHouseName();
    }

@Transactional
    public String detachDeviceFromHouse(UUID deviceId){
        Device device = utils.getDeviceById(deviceId);
        if(device.getHouse() == null){
            throw new InvalidAttachmentException("Device with name " + device.getDeviceName() + " is not attached to any house");
        }

        if(device.getRoom() != null){
            throw new InvalidAttachmentException("Device must be detached from the room first");
        }

        String currentUser = utils.getCurrentUsername();
        House house = utils.getHouseById(device.getHouse().getHouseId());

        if (!currentUser.equals(house.getAdmin())) {
            throw new AccessDeniedException(
                    "You are not allowed to remove this device from the house"
            );
        }

        house.getDevices().remove(device);
        deviceInventoryRepository.setRegisterToFalseByKickstonId(device.getKickstonId());

        device.setHouse(null);
        deviceRepository.save(device);

        return "Device with name " + device.getDeviceName() + " is detached from the house : " + house.getHouseName();
    }


    public String addDeviceToRoom(AddDeviceToRoomRequestDto addDeviceToRoomRequestDto){
        Device device = utils.getDeviceById(addDeviceToRoomRequestDto.getDeviceId());
        Room room = utils.getRoomById(addDeviceToRoomRequestDto.getRoomId());

        if(device.getRoom() != null){
            throw new InvalidAttachmentException("Device is already attached to a room please detach it first");
        }

        if (device.getHouse() == null) {
            throw new InvalidAttachmentException("Device must be attached to a house first");
        }

        if(!device.getHouse().getHouseId().equals(room.getHouse().getHouseId())){
            throw new InvalidAttachmentException("Device and Room are attached to different houses");
        }

        device.setRoom(room);

        Device savedDevice = deviceRepository.save(device);
        room.getDevices().add(savedDevice);

        return "Device " + device.getDeviceName() + " add to the room " + room.getRoomName() + " successfully";
    }


    public String detachDeviceFromTheRoom(UUID deviceId){
        Device device = utils.getDeviceById(deviceId);
        if(device.getRoom() == null){
            throw new InvalidAttachmentException("Device is not attached to any room");
        }
        Room room = utils.getRoomById(device.getRoom().getRoomId());
        room.getDevices().remove(device);
        String roomName = device.getRoom().getRoomName();
        device.setRoom(null);
        deviceRepository.save(device);
        return "Device " + device.getDeviceName() + " successfully detached from the room " + roomName;
    }


    public List<DeviceResponseDto> getDevicesByOwnerNotAttachedToHouseOrRoom() {
        String currentUsername = utils.getCurrentUsername();
        List<Device> devices = deviceRepository.getAllDevicesByOwnerNotAttachToHouseOrRoom(currentUsername);
        return devices
                .stream()
                .map(DeviceUtils::mapDeviceToDeviceResponseDto)
                .toList();
    }

    public List<DeviceResponseDto> getDevicesByHomeID(UUID homeId) {
        House house = utils.getHouseById(homeId);
        List<Device> devices = deviceRepository.findByHouse_houseIdAndDeletedAtIsNull(homeId);
        return devices
                .stream()
                .map(DeviceUtils::mapDeviceToDeviceResponseDto)
                .toList();
    }
}
