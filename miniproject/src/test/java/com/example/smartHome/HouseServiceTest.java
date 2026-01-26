package com.example.smartHome;


import com.example.smartHome.dto.HouseDtos.HouseResponseDto;
import com.example.smartHome.entity.House;
import com.example.smartHome.entity.HouseUser;
import com.example.smartHome.repository.*;
import com.example.smartHome.service.HouseService;
import com.example.smartHome.util.Utils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class HouseServiceTest {

        @Mock
        private HouseRepository houseRepository;

        @Mock
        private UserRepository userRepository;

        @Mock
        private DeviceInventoryRepository deviceInventoryRepository;

        @Mock
        private HouseUserRepository houseUserRepository;

        @Mock
        private DeviceRepository deviceRepository;

        @Mock
        private RoomRepository roomRepository;

        @Mock
        private Utils utils;

        @InjectMocks
        private HouseService houseService;

    @Test
    void shouldReturnAllHousesForCurrentUser() {

        String username = "venkat";

        House house1 = new House();
        house1.setHouseName("My Home");

        House house2 = new House();
        house2.setHouseName("Beach House");

        HouseUser hu1 = new HouseUser();
        hu1.setUser(username);
        hu1.setHouse(house1);

        HouseUser hu2 = new HouseUser();
        hu2.setUser(username);
        hu2.setHouse(house2);

        when(utils.getCurrentUsername()).thenReturn(username);
        when(houseUserRepository.findByUsername(username))
                .thenReturn(List.of(hu1, hu2));

        List<HouseResponseDto> result = houseService.getAllHouses();

        assertEquals(2, result.size());
        assertEquals("My Home", result.get(0).getHouseName());
        assertEquals("Beach House", result.get(1).getHouseName());

        verify(utils).getCurrentUsername();
        verify(houseUserRepository).findByUsername(username);
    }



}
