package com.marcusfromsweden.plantdoctor.controller;

import com.marcusfromsweden.plantdoctor.dto.GrowingLocationDTO;
import com.marcusfromsweden.plantdoctor.dto.PlantDTO;
import com.marcusfromsweden.plantdoctor.dto.PlantSpeciesDTO;
import com.marcusfromsweden.plantdoctor.service.PlantCommentService;
import com.marcusfromsweden.plantdoctor.service.PlantService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PlantController.class)
@AutoConfigureMockMvc
public class PlantControllerTests {

    public static final String API_PATH = "/api/plants";
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PlantService plantService;
    @MockBean
    private PlantCommentService plantCommentService;

    private PlantDTO plantDTO;
    private PlantSpeciesDTO plantSpeciesDTO;
    private GrowingLocationDTO growingLocationDTO;

    @BeforeEach
    public void setup() {
        plantSpeciesDTO = new PlantSpeciesDTO(1L, "Rose", "Beautiful flower", 7);
        growingLocationDTO = new GrowingLocationDTO(1L, "Clay pot nbr 1", true);
        plantDTO = new PlantDTO(
                1L,
                plantSpeciesDTO.id(),
                growingLocationDTO.id(),
                LocalDate.of(2025, 1, 1),
                LocalDate.of(2025, 1, 15));
    }

    @Test
    public void testGetAllPlants() throws Exception {
        Mockito.when(plantService.getAllPlants()).thenReturn(Collections.singletonList(plantDTO));

        mockMvc.perform(get(API_PATH)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(plantDTO.id().intValue())))
                .andExpect(jsonPath("$[0].plantSpeciesId", is(plantSpeciesDTO.id().intValue())))
                .andExpect(jsonPath("$[0].growingLocationId", is(growingLocationDTO.id().intValue())))
                .andExpect(jsonPath("$[0].plantingDate", is(plantDTO.plantingDate().toString())))
                .andExpect(jsonPath("$[0].germinationDate", is(plantDTO.germinationDate().toString())));
    }

    @Test
    public void testGetPlantById() throws Exception {
        Mockito.when(plantService.getPlantById(plantDTO.id())).thenReturn(Optional.of(plantDTO));

        mockMvc.perform(get(API_PATH + "/{id}", plantDTO.id())
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(plantDTO.id().intValue())))
                .andExpect(jsonPath("$.plantSpeciesId", is(plantSpeciesDTO.id().intValue())))
                .andExpect(jsonPath("$.growingLocationId", is(growingLocationDTO.id().intValue())))
                .andExpect(jsonPath("$.plantingDate", is(plantDTO.plantingDate().toString())))
                .andExpect(jsonPath("$.germinationDate", is(plantDTO.germinationDate().toString())));
    }

    @Test
    public void testCreatePlant() throws Exception {
        Mockito.when(plantService.createPlant(Mockito.any(PlantDTO.class)))
                .thenReturn(plantDTO);

        String plantJson = "{\"plantSpeciesId\":%d,\"growingLocationId\":%d,\"plantingDate\":\"%s\",\"germinationDate\":\"%s\"}".formatted(
                plantDTO.plantSpeciesId(),
                plantDTO.growingLocationId(),
                plantDTO.plantingDate().toString(),
                plantDTO.germinationDate().toString()
        );

        mockMvc.perform(post(API_PATH)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(plantJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(plantDTO.id().intValue())))
                .andExpect(jsonPath("$.plantSpeciesId", is(plantSpeciesDTO.id().intValue())))
                .andExpect(jsonPath("$.growingLocationId", is(growingLocationDTO.id().intValue())))
                .andExpect(jsonPath("$.plantingDate", is(plantDTO.plantingDate().toString())))
                .andExpect(jsonPath("$.germinationDate", is(plantDTO.germinationDate().toString())));
    }

    @Test
    public void testUpdatePlant() throws Exception {
        Mockito.when(plantService.updatePlant(plantDTO.id(), plantDTO)).thenReturn(plantDTO);

        String plantJson = "{\"id\":\"%s\",\"plantSpeciesId\":%d,\"growingLocationId\":%d,\"plantingDate\":\"%s\",\"germinationDate\":\"%s\"}".formatted(
                plantDTO.id(),
                plantDTO.plantSpeciesId(),
                plantDTO.growingLocationId(),
                plantDTO.plantingDate().toString(),
                plantDTO.germinationDate().toString()
        );

        mockMvc.perform(put(API_PATH + "/{id}", plantDTO.id())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(plantJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(plantDTO.id().intValue())))
                .andExpect(jsonPath("$.plantSpeciesId", is(plantSpeciesDTO.id().intValue())))
                .andExpect(jsonPath("$.growingLocationId", is(growingLocationDTO.id().intValue())))
                .andExpect(jsonPath("$.plantingDate", is(plantDTO.plantingDate().toString())))
                .andExpect(jsonPath("$.germinationDate", is(plantDTO.germinationDate().toString())));
    }

    @Test
    public void testDeletePlant() throws Exception {
        Mockito.doNothing().when(plantService).deletePlant(plantDTO.id());

        mockMvc.perform(delete(API_PATH + "/{id}", plantDTO.id())
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }
}