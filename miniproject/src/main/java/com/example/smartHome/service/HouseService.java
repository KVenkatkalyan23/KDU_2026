package com.example.smartHome.service;

import com.example.smartHome.dto.HouseDtos.AddUserToHouseRequest;
import com.example.smartHome.dto.HouseDtos.HouseRequestDto;
import com.example.smartHome.dto.HouseDtos.HouseResponseDto;
import com.example.smartHome.dto.HouseDtos.HouseUpdateRequestDto;
import com.example.smartHome.entity.Device;
import com.example.smartHome.entity.House;
import com.example.smartHome.entity.HouseUser;
import com.example.smartHome.entity.User;
import com.example.smartHome.exception.AccessDeniedException;
import com.example.smartHome.exception.InvalidOwnershipTransfer;
import com.example.smartHome.exception.UserAlreadyExistsException;
import com.example.smartHome.repository.*;
import com.example.smartHome.util.HouseUtils;
import com.example.smartHome.util.Utils;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.example.smartHome.util.HouseUtils.mapHouseRequestDtoToHouse;
import static com.example.smartHome.util.HouseUtils.mapHouseToHouseResponseDto;

@Service
@AllArgsConstructor
public class HouseService {

    private HouseRepository houseRepository;
    private UserRepository userRepository;
    private DeviceInventoryRepository deviceInventoryRepository;
    private HouseUserRepository houseUserRepository;
    private DeviceRepository deviceRepository;
    private RoomRepository roomRepository;
    private Utils utils;

    @Transactional
    public HouseResponseDto addHouse(HouseRequestDto houseRequestDto) {
        House house = mapHouseRequestDtoToHouse(houseRequestDto);
        String currentUserName = utils.getCurrentUsername();

        Optional<User> optionalUser = userRepository.findById(currentUserName);
        User user = optionalUser.get();

        house.setAdmin(user.getUsername());
        house.setCreatedAt(LocalDateTime.now());
        House savedHouse = houseRepository.save(house);

        HouseUser houseUser = new HouseUser();
        houseUser.setHouse(savedHouse);
        houseUser.setUser(user.getUsername());
        houseUserRepository.save(houseUser);

        return mapHouseToHouseResponseDto(savedHouse);
    }


    public List<HouseResponseDto> getAllHousesByAdmin() {
        String currentUsername = utils.getCurrentUsername();
        List<House> houses = houseRepository.getAllHousesByUsername(currentUsername);
        return houses
                .stream()
                .map(HouseUtils::mapHouseToHouseResponseDto)
                .toList();
    }

    public String addUserToHouse(AddUserToHouseRequest addUserToHouseRequest) {
        User user = utils.getUserById(addUserToHouseRequest.getUsername());
        House house = utils.getHouseById(addUserToHouseRequest.getHouseId());

        String currentUsername = utils.getCurrentUsername();
        if(!house.getAdmin().equals(currentUsername)){
            throw new AccessDeniedException("User should be Admin of the house to add a user");
        }

        if(houseUserRepository.existsByHouse_HouseIdAndUser(addUserToHouseRequest.getHouseId(),addUserToHouseRequest.getUsername())){
            throw new UserAlreadyExistsException("User already exists in house " + house.getHouseName() +  " with username " + addUserToHouseRequest.getUsername());
        }

        HouseUser houseUser = new HouseUser();
        houseUser.setUser(user.getUsername());
        houseUser.setHouse(house);
        houseUserRepository.save(houseUser);

        return addUserToHouseRequest.getUsername() + " successfully added to the house " + house.getHouseName();
    }

    @Transactional
    public String transferOwnership(String username, UUID houseId) {
        User nextOwner = utils.getUserById(username);
        House house = utils.getHouseById(houseId);

        if(!houseUserRepository.existsByHouse_HouseIdAndUser(houseId,username)){
            throw new InvalidOwnershipTransfer(username + " must be a member of the house to transfer ownership");
        }

        String currentUsername = utils.getCurrentUsername();
        if(!house.getAdmin().equals(currentUsername)){
            throw new AccessDeniedException("User should be Admin of the house to transfer Ownership");
        }

        house.setAdmin(username);
        deviceRepository.updateDeviceOwnership(username,houseId);
        houseRepository.save(house);
        return "Ownership of house " + house.getHouseName() + " transferred to " + username;
    }



    public HouseResponseDto updateHouse(HouseUpdateRequestDto houseUpdateRequestDto) {
        House house = utils.getHouseById(houseUpdateRequestDto.getHouseId());

        String currentUser = utils.getCurrentUsername();
        if (!currentUser.equals(house.getAdmin())) {
            throw new AccessDeniedException("Only house admin can Update the House");
        }

        house.setHouseName(houseUpdateRequestDto.getHouseName());
        house.setCity(houseUpdateRequestDto.getCity());
        house.setAddress(houseUpdateRequestDto.getAddress());
        house.setState(houseUpdateRequestDto.getState());
        house.setPostalCode(houseUpdateRequestDto.getPostalCode());
        house.setModifiedAt(LocalDateTime.now());

        House savedHouse = houseRepository.save(house);
        return mapHouseToHouseResponseDto(savedHouse);
    }


    @Transactional
    public String softDeleteHouse(UUID houseId) {
        House house = utils.getHouseById(houseId);
        String currentUsername = utils.getCurrentUsername();

        if(!house.getAdmin().equals(currentUsername)){
            throw new AccessDeniedException("User should be Admin of the house to transfer Ownership");
        }

        List<Device> devices = deviceRepository.findByHouse_houseIdAndDeletedAtIsNull(houseId);

        List<String> kickstonIds = devices
                .stream()
                .map(Device::getKickstonId)
                .toList();

        List<UUID> deviceIds = devices
                .stream()
                        .map(Device::getDeviceId)
                                .toList();

        houseRepository.softDeleteByHouseId(houseId);
        houseUserRepository.softDeleteHouseUserByHouseId(houseId);
        roomRepository.softDeleteByHouseId(houseId);
        deviceInventoryRepository.setRegisterToFalseByKickstonIds(kickstonIds);
        deviceRepository.softDeleteDevicesByDeviceIds(deviceIds);
        deviceRepository.detachDevicesFromHouse(houseId);

        return "House successfully deleted with houseId : " + houseId;
    }

    public List<HouseResponseDto> getAllHouses() {
        String currentUsername = utils.getCurrentUsername();
        List<House> houses = houseUserRepository.findByUsername(currentUsername)
                .stream()
                .map(HouseUser::getHouse)
                .toList();

        return houses
                .stream()
                .map(HouseUtils::mapHouseToHouseResponseDto)
                .toList();
    }
}
