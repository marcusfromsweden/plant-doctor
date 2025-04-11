package com.marcusfromsweden.plantdoctor.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.marcusfromsweden.plantdoctor.config.TestSecurityConfig;
import com.marcusfromsweden.plantdoctor.dto.GrowingLocationDTO;
import com.marcusfromsweden.plantdoctor.dto.mapper.GrowingLocationMapper;
import com.marcusfromsweden.plantdoctor.service.GrowingLocationService;
import com.marcusfromsweden.plantdoctor.service.JwtService;
import com.marcusfromsweden.plantdoctor.util.GrowingLocationTestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(GrowingLocationController.class)
@AutoConfigureMockMvc
@Import({GrowingLocationTestHelper.class, GrowingLocationMapper.class, TestSecurityConfig.class})
public class GrowingLocationControllerTests {

    private static final String API_PATH_GROWING_LOCATIONS = "/api/growing-locations";
    private static final long EXISTING_GROWING_LOCATION_ID = 1L;
    private static final String EXISTING_GROWING_LOCATION_NAME = "Pot 1";
    private static final long UPDATED_GROWING_LOCATION_ID = 2L;
    private static final String UPDATED_GROWING_LOCATION_NAME = "Updated Pot";

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private GrowingLocationTestHelper growingLocationTestHelper;

    @MockitoBean
    private GrowingLocationService growingLocationService;
    @MockitoBean
    private JwtService jwtService;

    private GrowingLocationDTO existingGrowingLocationDTO;
    private GrowingLocationDTO updatedGrowingLocationDTO;

    @BeforeEach
    public void setupTest() {
        existingGrowingLocationDTO = growingLocationTestHelper.createDTO(
                EXISTING_GROWING_LOCATION_ID,
                EXISTING_GROWING_LOCATION_NAME
        );

        updatedGrowingLocationDTO = growingLocationTestHelper.createDTO(
                UPDATED_GROWING_LOCATION_ID,
                UPDATED_GROWING_LOCATION_NAME
        );
    }

    @Test
    public void shouldGetAllGrowingLocations() throws Exception {
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
    public void shouldGetGrowingLocationById() throws Exception {
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
    public void shouldCreateGrowingLocation() throws Exception {
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
    public void shouldUpdateGrowingLocation() throws Exception {
        Mockito.when(growingLocationService.updateGrowingLocation(Mockito.eq(existingGrowingLocationDTO.id()),
                                                                  Mockito.any(GrowingLocationDTO.class)))
                .thenReturn(updatedGrowingLocationDTO);
        String growingLocationJson = objectMapper.writeValueAsString(updatedGrowingLocationDTO);

        mockMvc.perform(put(API_PATH_GROWING_LOCATIONS + "/{id}", existingGrowingLocationDTO.id())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(growingLocationJson)).andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(updatedGrowingLocationDTO.id().intValue())))
                .andExpect(jsonPath("$.name", is(updatedGrowingLocationDTO.name())));

        Mockito.verify(growingLocationService, Mockito.times(1))
                .updateGrowingLocation(
                        Mockito.eq(existingGrowingLocationDTO.id()),
                        Mockito.any(GrowingLocationDTO.class));
    }

    @Test
    public void shouldGetGrowingLocationByName() throws Exception {
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
    public void shouldDeleteGrowingLocation() throws Exception {
        Mockito.doNothing().when(growingLocationService)
                .deleteGrowingLocation(existingGrowingLocationDTO.id());

        mockMvc.perform(delete(API_PATH_GROWING_LOCATIONS + "/{id}", existingGrowingLocationDTO.id())
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        Mockito.verify(growingLocationService, Mockito.times(1))
                .deleteGrowingLocation(existingGrowingLocationDTO.id());
    }
}