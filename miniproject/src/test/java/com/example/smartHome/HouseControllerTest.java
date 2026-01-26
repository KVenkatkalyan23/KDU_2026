//package com.example.smartHome;
//
//
//import com.example.smartHome.controller.HouseController;
//import com.example.smartHome.dto.HouseDtos.HouseResponseDto;
//import com.example.smartHome.service.HouseService;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mockito;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//
//import java.util.List;
//import java.util.UUID;
//
//import static org.mockito.Mockito.when;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
////@WebMvcTest(HouseController.class)
//class HouseControllerTest {
//
////    @Autowired
////    private MockMvc mockMvc;
////
////    @MockBean
////    private HouseService houseService;
////
////    @Autowired
////    private ObjectMapper objectMapper;
////
////    @Test
////    void shouldReturnAllHouses() throws Exception {
////
////        UUID houseId1 = UUID.randomUUID();
////        UUID houseId2 = UUID.randomUUID();
////
////        HouseResponseDto house1 = new HouseResponseDto();
////        house1.setHouseId(houseId1);
////        house1.setHouseName("Dream Home");
////
////        HouseResponseDto house2 = new HouseResponseDto();
////        house2.setHouseId(houseId2);
////        house2.setHouseName("Vacation House");
////
////        List<HouseResponseDto> mockHouses = List.of(house1, house2);
////
////        when(houseService.getAllHouses()).thenReturn(mockHouses);
////
////        mockMvc.perform(get("/api/v1/house"))
////                .contentType(MediaType.APPLICATION_JSON))
////                .andExpect(status().isOk())
////                .andExpect(content().json(objectMapper.writeValueAsString(mockHouses)));
////
////
////        Mockito.verify(houseService).getAllHouses();
////    }
//}
