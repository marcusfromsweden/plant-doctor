package com.marcusfromsweden.plantdoctor.controller;

import com.marcusfromsweden.plantdoctor.dto.PlantSpeciesDTO;
import com.marcusfromsweden.plantdoctor.service.PlantSpeciesService;
import org.junit.jupiter.api.BeforeEach;
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
public class PlantSpeciesControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PlantSpeciesService plantSpeciesService;

    private PlantSpeciesDTO plantSpeciesDTO;

    @BeforeEach
    public void setup() {
        plantSpeciesDTO = PlantSpeciesDTO.builder()
                .id(1L)
                .name("Tomato")
                .description("A tasty treat")
                .estimatedDaysToGermination(7)
                .build();
    }

    @Test
    public void testGetAllPlantSpecies() throws Exception {
        Mockito.when(plantSpeciesService.getAllPlantSpecies())
                .thenReturn(Collections.singletonList(plantSpeciesDTO));

        mockMvc.perform(get("/api/plant-species").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(plantSpeciesDTO.id().intValue())))
                .andExpect(jsonPath("$[0].name", is(plantSpeciesDTO.name())))
                .andExpect(jsonPath("$[0].description", is(plantSpeciesDTO.description())))
                .andExpect(jsonPath("$[0].estimatedDaysToGermination", is(plantSpeciesDTO.estimatedDaysToGermination())));
    }

    @Test
    public void testGetPlantSpeciesById() throws Exception {
        Mockito.when(plantSpeciesService.getPlantSpeciesById(plantSpeciesDTO.id()))
                .thenReturn(Optional.of(plantSpeciesDTO));

        mockMvc.perform(get("/api/plant-species/{id}", plantSpeciesDTO.id())
                        .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(plantSpeciesDTO.id().intValue())))
                .andExpect(jsonPath("$.name", is(plantSpeciesDTO.name())))
                .andExpect(jsonPath("$.description", is(plantSpeciesDTO.description())))
                .andExpect(jsonPath("$.estimatedDaysToGermination", is(plantSpeciesDTO.estimatedDaysToGermination())));
    }

    @Test
    public void testCreatePlantSpecies() throws Exception {
        Mockito.when(plantSpeciesService.createPlantSpecies(Mockito.any(PlantSpeciesDTO.class)))
                .thenReturn(plantSpeciesDTO);

        String plantSpeciesJson = "{\"name\":\"%s\",\"description\":\"%s\",\"estimatedDaysToGermination\":%d}"
                .formatted(plantSpeciesDTO.name(), plantSpeciesDTO.description(), plantSpeciesDTO.estimatedDaysToGermination());

        mockMvc.perform(post("/api/plant-species").contentType(MediaType.APPLICATION_JSON)
                        .content(plantSpeciesJson)).andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(plantSpeciesDTO.id().intValue())))
                .andExpect(jsonPath("$.name", is(plantSpeciesDTO.name())))
                .andExpect(jsonPath("$.description", is(plantSpeciesDTO.description())))
                .andExpect(jsonPath("$.estimatedDaysToGermination", is(plantSpeciesDTO.estimatedDaysToGermination())));
    }

    @Test
    public void testUpdatePlantSpecies() throws Exception {
        Mockito.when(plantSpeciesService.updatePlantSpecies(Mockito.eq(plantSpeciesDTO.id()),
                Mockito.any(PlantSpeciesDTO.class))).thenReturn(plantSpeciesDTO);

        String plantSpeciesJson = "{\"name\":\"%s\",\"description\":\"%s\",\"estimatedDaysToGermination\":%d}"
                .formatted(plantSpeciesDTO.name(), plantSpeciesDTO.description(), plantSpeciesDTO.estimatedDaysToGermination());

        mockMvc.perform(put("/api/plant-species/{id}", plantSpeciesDTO.id())
                        .contentType(MediaType.APPLICATION_JSON).content(plantSpeciesJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(plantSpeciesDTO.id().intValue())))
                .andExpect(jsonPath("$.name", is(plantSpeciesDTO.name())))
                .andExpect(jsonPath("$.description", is(plantSpeciesDTO.description())))
                .andExpect(jsonPath("$.estimatedDaysToGermination", is(plantSpeciesDTO.estimatedDaysToGermination())));
    }

    @Test
    public void testDeletePlantSpecies() throws Exception {
        Mockito.doNothing().when(plantSpeciesService).deletePlantSpecies(plantSpeciesDTO.id());

        mockMvc.perform(delete("/api/plant-species/{id}", plantSpeciesDTO.id())
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isNoContent());
    }
}