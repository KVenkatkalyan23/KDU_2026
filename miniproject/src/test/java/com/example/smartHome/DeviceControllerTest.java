package com.example.smartHome;

import com.example.smartHome.controller.DeviceController;
import com.example.smartHome.dto.DeviceDtos.AddDeviceToRoomRequestDto;
import com.example.smartHome.service.DeviceService;
import com.example.smartHome.util.JwtFilter;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(DeviceController.class)
@AutoConfigureMockMvc(addFilters = false)
class DeviceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DeviceService deviceService;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private JwtFilter jwtFilter;

    @Test
    void shouldDetachThenAddDeviceToAnotherRoom() throws Exception {

        UUID deviceId = UUID.randomUUID();
        UUID roomId = UUID.randomUUID();

        AddDeviceToRoomRequestDto requestDto =
                new AddDeviceToRoomRequestDto(deviceId, roomId);

        Mockito.when(deviceService.detachDeviceFromTheRoom(deviceId))
                .thenReturn("Device detached");

        Mockito.when(deviceService.addDeviceToRoom(Mockito.any()))
                .thenReturn("Device added to room");

        mockMvc.perform(post("/api/v1/device/detachfromroom")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(deviceId)))
                .andExpect(status().isOk());

        mockMvc.perform(post("/api/v1/device/addtoroom")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isOk());

        Mockito.verify(deviceService).detachDeviceFromTheRoom(deviceId);
        Mockito.verify(deviceService).addDeviceToRoom(Mockito.any());
    }
}
