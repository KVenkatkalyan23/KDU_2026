//package com.example.smartHome.controller;
//
//import com.example.smartHome.entity.*;
//import com.example.smartHome.repository.*;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.web.client.TestRestTemplate;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.UUID;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@ActiveProfiles("test")
//@Transactional
////public class HouseControllerIntegrationTest {
////
////    @Autowired
////    private TestRestTemplate restTemplate;
////
////    @Autowired
////    private HouseRepository houseRepository;
////
////    @Autowired
////    private RoomRepository roomRepository;
////
////    @Autowired
////    private DeviceRepository deviceRepository;
////
////    private UUID houseId;
////
////    @BeforeEach
////    public void setup() {
////        House house = new House();
////        house.setHouseName("Test House");
////        house.setAddress("123 Test St");
////        house = houseRepository.save(house);
////        houseId = house.getHouseId();
////
////        Room livingRoom = new Room();
////        livingRoom.setRoomName("Living Room");
////        livingRoom.setHouse(house);
////        livingRoom = roomRepository.save(livingRoom);
////
////        Device tv = new Device();
////        tv.setDeviceName("Smart TV");
////        tv.setRoom(livingRoom);
////        deviceRepository.save(tv);
////    }
////
////    @Test
////    public void testGetRoomsAndDevices_Success() {
////        String url = "/api/v1/house/" + houseId + "/rooms";
////
////        var response = restTemplate.getForEntity(url, String.class);
////
////        assertThat(response.getStatusCodeValue()).isEqualTo(200);
////        assertThat(response.getBody()).contains("Rooms and devices retrieved successfully");
////    }
////}