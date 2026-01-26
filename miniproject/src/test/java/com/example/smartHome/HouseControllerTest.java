package com.example.smartHome;

import com.example.smartHome.controller.HouseController;
import com.example.smartHome.dto.HouseDtos.HouseResponseDto;
import com.example.smartHome.service.HouseService;
import com.example.smartHome.util.JwtFilter;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(HouseController.class)
@AutoConfigureMockMvc(addFilters = false) // disables security filters
class HouseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private HouseService houseService;

    @MockBean
    private JwtFilter jwtFilter;

    @Test
    void shouldReturnAllHouses() throws Exception {

        HouseResponseDto house1 = new HouseResponseDto();
        house1.setHouseId(UUID.randomUUID());
        house1.setHouseName("Dream Home");

        HouseResponseDto house2 = new HouseResponseDto();
        house2.setHouseId(UUID.randomUUID());
        house2.setHouseName("Vacation House");

        when(houseService.getAllHouses())
                .thenReturn(List.of(house1, house2));

        mockMvc.perform(get("/api/v1/house")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("List of all Houses"))
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data.length()").value(2))
                .andExpect(jsonPath("$.data[0].houseName").value("Dream Home"))
                .andExpect(jsonPath("$.data[1].houseName").value("Vacation House"));
    }
}
