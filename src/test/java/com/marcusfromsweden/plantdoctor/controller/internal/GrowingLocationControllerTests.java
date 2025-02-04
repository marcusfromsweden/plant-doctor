package com.marcusfromsweden.plantdoctor.controller.internal;

import com.marcusfromsweden.plantdoctor.dto.GrowingLocationDTO;
import com.marcusfromsweden.plantdoctor.service.GrowingLocationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger log = LoggerFactory.getLogger(GrowingLocationControllerTests.class);

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GrowingLocationService growingLocationService;

    private GrowingLocationDTO growingLocationDTO;

    @BeforeEach
    public void setup() {
        growingLocationDTO = GrowingLocationDTO.builder()
                .id(1L)
                .locationName("Pot 1")
                .occupied(true)
                .build();
    }

    @Test
    public void testGetAllGrowingLocations() throws Exception {
        log.info("Getting all growing locations");
        Mockito.when(growingLocationService.getAllGrowingLocations())
                .thenReturn(Collections.singletonList(growingLocationDTO));

        mockMvc.perform(get("/api/internal/growing-locations")
                        .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(growingLocationDTO.id().intValue())))
                .andExpect(jsonPath("$[0].locationName", is(growingLocationDTO.locationName())))
                .andExpect(jsonPath("$[0].occupied", is(growingLocationDTO.occupied())));
    }

    @Test
    public void testGetGrowingLocationById() throws Exception {
        Mockito.when(growingLocationService.getGrowingLocationById(growingLocationDTO.id()))
                .thenReturn(Optional.of(growingLocationDTO));

        mockMvc.perform(get("/api/internal/growing-locations/{id}", growingLocationDTO.id())
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

        mockMvc.perform(post("/api/internal/growing-locations")
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

        mockMvc.perform(put("/api/internal/growing-locations/{id}", growingLocationDTO.id())
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

        mockMvc.perform(delete("/api/internal/growing-locations/{id}", growingLocationDTO.id())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }
}