package com.marcusfromsweden.plantdoctor.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.marcusfromsweden.plantdoctor.dto.GrowingLocationDTO;
import com.marcusfromsweden.plantdoctor.service.GrowingLocationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(GrowingLocationController.class)
@AutoConfigureMockMvc
public class GrowingLocationControllerTests {

    private static final Logger log = LoggerFactory.getLogger(GrowingLocationControllerTests.class);
    public static final String API_PATH_GROWING_LOCATIONS = "/api/growing-locations";

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private GrowingLocationService growingLocationService;

    private GrowingLocationDTO existingGrowingLocationDTO;
    private GrowingLocationDTO updatedGrowingLocation;

    @BeforeEach
    public void setup() {
        existingGrowingLocationDTO = GrowingLocationDTO.builder()
                .id(1L)
                .name("Pot 1")
                .build();

        // Separate DTO with modified data for update
        updatedGrowingLocation = GrowingLocationDTO.builder()
                .id(1L)
                .name("Updated Pot")
                .build();
    }

    @Test
    public void testGetAllGrowingLocations() throws Exception {
        Mockito.when(growingLocationService.getAllGrowingLocations())
                .thenReturn(Collections.singletonList(existingGrowingLocationDTO));

        mockMvc.perform(get(API_PATH_GROWING_LOCATIONS)
                                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(existingGrowingLocationDTO.id().intValue())))
                .andExpect(jsonPath("$[0].name", is(existingGrowingLocationDTO.name())));

        Mockito.verify(growingLocationService, Mockito.times(1))
                .getAllGrowingLocations();
    }

    @Test
    public void testGetGrowingLocationById() throws Exception {
        Mockito.when(growingLocationService.getGrowingLocationById(existingGrowingLocationDTO.id()))
                .thenReturn(Optional.of(existingGrowingLocationDTO));

        mockMvc.perform(get(API_PATH_GROWING_LOCATIONS + "/{id}", existingGrowingLocationDTO.id())
                                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(existingGrowingLocationDTO.id().intValue())))
                .andExpect(jsonPath("$.name", is(existingGrowingLocationDTO.name())));

        Mockito.verify(growingLocationService, Mockito.times(1))
                .getGrowingLocationById(existingGrowingLocationDTO.id());
    }

    @Test
    public void testCreateGrowingLocation() throws Exception {
        Mockito.when(growingLocationService.createGrowingLocation(Mockito.any(GrowingLocationDTO.class)))
                .thenReturn(existingGrowingLocationDTO);
        String growingLocationJson = objectMapper.writeValueAsString(existingGrowingLocationDTO);

        mockMvc.perform(post(API_PATH_GROWING_LOCATIONS)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(growingLocationJson)).andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(existingGrowingLocationDTO.id().intValue())))
                .andExpect(jsonPath("$.name", is(existingGrowingLocationDTO.name())));

        Mockito.verify(growingLocationService, Mockito.times(1))
                .createGrowingLocation(Mockito.any(GrowingLocationDTO.class));
    }

    @Test
    public void testUpdateGrowingLocation() throws Exception {
        Mockito.when(growingLocationService.updateGrowingLocation(
                Mockito.eq(existingGrowingLocationDTO.id()),
                Mockito.any(GrowingLocationDTO.class))).thenReturn(updatedGrowingLocation);
        String growingLocationJson = objectMapper.writeValueAsString(updatedGrowingLocation);

        mockMvc.perform(put(API_PATH_GROWING_LOCATIONS + "/{id}", existingGrowingLocationDTO.id())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(growingLocationJson)).andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(updatedGrowingLocation.id().intValue())))
                .andExpect(jsonPath("$.name", is(updatedGrowingLocation.name())));

        Mockito.verify(growingLocationService, Mockito.times(1))
                .updateGrowingLocation(
                        Mockito.eq(existingGrowingLocationDTO.id()),
                        Mockito.any(GrowingLocationDTO.class));
    }

    @Test
    public void testGetGrowingLocationByName() throws Exception {
        String name = "Pot 1";
        Mockito.when(growingLocationService.getGrowingLocationByName(name))
                .thenReturn(Optional.of(existingGrowingLocationDTO));

        mockMvc.perform(get(API_PATH_GROWING_LOCATIONS + "/name/{name}", name)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(existingGrowingLocationDTO.id().intValue())))
                .andExpect(jsonPath("$.name", is(existingGrowingLocationDTO.name())));

        Mockito.verify(growingLocationService, Mockito.times(1))
                .getGrowingLocationByName(name);
    }

    @Test
    public void testDeleteGrowingLocation() throws Exception {
        Mockito.doNothing().when(growingLocationService)
                .deleteGrowingLocation(existingGrowingLocationDTO.id());

        mockMvc.perform(delete(API_PATH_GROWING_LOCATIONS + "/{id}", existingGrowingLocationDTO.id())
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        Mockito.verify(growingLocationService, Mockito.times(1))
                .deleteGrowingLocation(existingGrowingLocationDTO.id());
    }
}