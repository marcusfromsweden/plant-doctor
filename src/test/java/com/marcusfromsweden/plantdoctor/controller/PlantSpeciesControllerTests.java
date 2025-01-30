package com.marcusfromsweden.plantdoctor.controller;

import com.marcusfromsweden.plantdoctor.entity.PlantSpecies;
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

    private PlantSpecies plantSpecies;

    @BeforeEach
    public void setup() {
        plantSpecies = new PlantSpecies();
        plantSpecies.setId(1L);
        plantSpecies.setName("Tomato");
        plantSpecies.setDescription("A tasty treat");
    }

    @Test
    public void testGetAllPlantSpecies() throws Exception {
        Mockito.when(plantSpeciesService.getAllPlantSpecies())
                .thenReturn(Collections.singletonList(plantSpecies));

        mockMvc.perform(get("/api/plant-species").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(plantSpecies.getId().intValue())))
                .andExpect(jsonPath("$[0].name", is(plantSpecies.getName())))
                .andExpect(jsonPath("$[0].description", is(plantSpecies.getDescription())));
    }

    @Test
    public void testGetPlantSpeciesById() throws Exception {
        Mockito.when(plantSpeciesService.getPlantSpeciesById(plantSpecies.getId()))
                .thenReturn(Optional.of(plantSpecies));

        mockMvc.perform(get("/api/plant-species/{id}", plantSpecies.getId())
                        .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(plantSpecies.getId().intValue())))
                .andExpect(jsonPath("$.name", is(plantSpecies.getName())))
                .andExpect(jsonPath("$.description", is(plantSpecies.getDescription())));
    }

    @Test
    public void testCreatePlantSpecies() throws Exception {
        Mockito.when(plantSpeciesService.createPlantSpecies(Mockito.any(PlantSpecies.class)))
                .thenReturn(plantSpecies);

        String plantSpeciesJson = "{\"name\":\"%s\",\"description\":\"%s\"}"
                .formatted(plantSpecies.getName(), plantSpecies.getDescription());

        mockMvc.perform(post("/api/plant-species").contentType(MediaType.APPLICATION_JSON)
                        .content(plantSpeciesJson)).andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(plantSpecies.getId().intValue())))
                .andExpect(jsonPath("$.name", is(plantSpecies.getName())))
                .andExpect(jsonPath("$.description", is(plantSpecies.getDescription())));
    }

    @Test
    public void testUpdatePlantSpecies() throws Exception {
        Mockito.when(plantSpeciesService.updatePlantSpecies(Mockito.eq(plantSpecies.getId()),
                Mockito.any(PlantSpecies.class))).thenReturn(plantSpecies);

        String plantSpeciesJson = "{\"name\":\"%s\",\"description\":\"%s\"}"
                .formatted(plantSpecies.getName(), plantSpecies.getDescription());

        mockMvc.perform(put("/api/plant-species/{id}", plantSpecies.getId())
                        .contentType(MediaType.APPLICATION_JSON).content(plantSpeciesJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(plantSpecies.getId().intValue())))
                .andExpect(jsonPath("$.name", is(plantSpecies.getName())))
                .andExpect(jsonPath("$.description", is(plantSpecies.getDescription())));
    }

    @Test
    public void testDeletePlantSpecies() throws Exception {
        Mockito.doNothing().when(plantSpeciesService).deletePlantSpecies(plantSpecies.getId());

        mockMvc.perform(delete("/api/plant-species/{id}", plantSpecies.getId())
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isNoContent());
    }
}