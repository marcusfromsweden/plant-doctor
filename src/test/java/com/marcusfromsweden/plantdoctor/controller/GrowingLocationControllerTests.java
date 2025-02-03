package com.marcusfromsweden.plantdoctor.controller;

import com.marcusfromsweden.plantdoctor.dto.GrowingLocationDTO;
import com.marcusfromsweden.plantdoctor.service.GrowingLocationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class GrowingLocationControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GrowingLocationService growingLocationService;

    private GrowingLocationDTO growingLocationDTO;

    @BeforeEach
    public void setup() {
        growingLocationDTO = new GrowingLocationDTO(1L, "Pot 1", true);
    }

    @Test
    public void testGetAllGrowingLocations() throws Exception {
        Mockito.when(growingLocationService.getAllGrowingLocations())
                .thenReturn(Collections.singletonList(growingLocationDTO));

        mockMvc.perform(get("/api/growing-locations")
                        .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(growingLocationDTO.id().intValue())))
                .andExpect(jsonPath("$[0].locationName", is(growingLocationDTO.locationName())))
                .andExpect(jsonPath("$[0].occupied", is(growingLocationDTO.occupied())));
    }

    @Test
    public void testGetGrowingLocationById() throws Exception {
        Mockito.when(growingLocationService.getGrowingLocationById(growingLocationDTO.id()))
                .thenReturn(Optional.of(growingLocationDTO));

        mockMvc.perform(get("/api/growing-locations/{id}", growingLocationDTO.id())
                        .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(growingLocationDTO.id().intValue())))
                .andExpect(jsonPath("$.locationName", is(growingLocationDTO.locationName())))
                .andExpect(jsonPath("$.occupied", is(growingLocationDTO.occupied())));
    }

    @Test
    public void testCreateGrowingLocation() throws Exception {
        Mockito.when(growingLocationService.createGrowingLocation(Mockito.any(GrowingLocationDTO.class)))
                .thenReturn(growingLocationDTO);

        String growingLocationJson = "{\"locationName\":\"%s\",\"occupied\":%b}".formatted(
                growingLocationDTO.locationName(), growingLocationDTO.occupied());

        mockMvc.perform(post("/api/growing-locations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(growingLocationJson)).andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(growingLocationDTO.id().intValue())))
                .andExpect(jsonPath("$.locationName", is(growingLocationDTO.locationName())))
                .andExpect(jsonPath("$.occupied", is(growingLocationDTO.occupied())));
    }

    @Test
    @Disabled
    public void testUpdateGrowingLocation() throws Exception {
        Mockito.when(growingLocationService.updateGrowingLocation(
                Mockito.eq(growingLocationDTO.id()),
                Mockito.any(GrowingLocationDTO.class))).thenReturn(growingLocationDTO);

        String growingLocationJson =
                "{\"id\":\"%s\",\"locationName\":\"%s\",\"occupied\":%b}".formatted(
                        growingLocationDTO.id(),
                        growingLocationDTO.locationName(),
                        growingLocationDTO.occupied());

        mockMvc.perform(put("/api/growing-locations/{id}", growingLocationDTO.id())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(growingLocationJson)).andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(growingLocationDTO.id().intValue())))
                .andExpect(jsonPath("$.locationName", is(growingLocationDTO.locationName())))
                .andExpect(jsonPath("$.occupied", is(growingLocationDTO.occupied())));
    }

    @Test
    public void testDeleteGrowingLocation() throws Exception {
        Mockito.doNothing().when(growingLocationService)
                .deleteGrowingLocation(growingLocationDTO.id());

        mockMvc.perform(delete("/api/growing-locations/{id}", growingLocationDTO.id())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }
}