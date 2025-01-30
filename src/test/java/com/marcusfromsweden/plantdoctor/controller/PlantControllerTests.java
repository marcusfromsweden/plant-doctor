package com.marcusfromsweden.plantdoctor.controller;

import com.marcusfromsweden.plantdoctor.entity.GrowingLocation;
import com.marcusfromsweden.plantdoctor.entity.Plant;
import com.marcusfromsweden.plantdoctor.entity.PlantSpecies;
import com.marcusfromsweden.plantdoctor.service.PlantService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class PlantControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PlantService plantService;

    private Plant plant;
    private PlantSpecies plantSpecies;
    private GrowingLocation growingLocation;

    @BeforeEach
    public void setup() {
        plantSpecies = new PlantSpecies();
        plantSpecies.setId(1L);
        plantSpecies.setName("Rose");
        plantSpecies.setDescription("Beautiful flower");

        growingLocation = new GrowingLocation();
        growingLocation.setId(1L);
        growingLocation.setLocationName("Clay pot nbr 1");
        growingLocation.setOccupied(true);

        plant = new Plant();
        plant.setId(1L);
        plant.setPlantSpecies(plantSpecies);
        plant.setGrowingLocation(growingLocation);
        plant.setPlantingDate(LocalDate.of(2025, 1, 1));
        plant.setGerminationDate(LocalDate.of(2025, 1, 15));
        plant.setComment("Healthy plant");
    }

    @Test
    public void testGetAllPlants() throws Exception {
        Mockito.when(plantService.getAllPlants()).thenReturn(Collections.singletonList(plant));

        mockMvc.perform(get("/api/plants")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(plant.getId().intValue())))
                .andExpect(jsonPath("$[0].plantSpecies.id", is(plantSpecies.getId().intValue())))
                .andExpect(jsonPath("$[0].growingLocation.id", is(growingLocation.getId().intValue())))
                .andExpect(jsonPath("$[0].plantingDate", is(plant.getPlantingDate().toString())))
                .andExpect(jsonPath("$[0].germinationDate", is(plant.getGerminationDate().toString())))
                .andExpect(jsonPath("$[0].comment", is(plant.getComment())));
    }

    @Test
    public void testGetPlantById() throws Exception {
        Mockito.when(plantService.getPlantById(plant.getId())).thenReturn(Optional.of(plant));

        mockMvc.perform(get("/api/plants/{id}", plant.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(plant.getId().intValue())))
                .andExpect(jsonPath("$.plantSpecies.id", is(plantSpecies.getId().intValue())))
                .andExpect(jsonPath("$.growingLocation.id", is(growingLocation.getId().intValue())))
                .andExpect(jsonPath("$.plantingDate", is(plant.getPlantingDate().toString())))
                .andExpect(jsonPath("$.germinationDate", is(plant.getGerminationDate().toString())))
                .andExpect(jsonPath("$.comment", is(plant.getComment())));
    }

    @Test
    public void testCreatePlant() throws Exception {
        Mockito.when(plantService.addPlant(
                plant.getPlantSpecies().getId(),
                plant.getGrowingLocation().getId(),
                plant.getPlantingDate(),
                plant.getGerminationDate(),
                plant.getComment()
        )).thenReturn(plant);

        String plantJson = "{\"plantSpecies\":{\"id\":%d},\"growingLocation\":{\"id\":%d},\"plantingDate\":\"%s\",\"germinationDate\":\"%s\",\"comment\":\"%s\"}".formatted(
                plant.getPlantSpecies().getId(),
                plant.getGrowingLocation().getId(),
                plant.getPlantingDate().toString(),
                plant.getGerminationDate().toString(),
                plant.getComment()
        );

        mockMvc.perform(post("/api/plants")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(plantJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(plant.getId().intValue())))
                .andExpect(jsonPath("$.plantSpecies.id", is(plantSpecies.getId().intValue())))
                .andExpect(jsonPath("$.growingLocation.id", is(growingLocation.getId().intValue())))
                .andExpect(jsonPath("$.plantingDate", is(plant.getPlantingDate().toString())))
                .andExpect(jsonPath("$.germinationDate", is(plant.getGerminationDate().toString())))
                .andExpect(jsonPath("$.comment", is(plant.getComment())));
    }

    @Test
    public void testUpdatePlant() throws Exception {
        Mockito.when(plantService.updatePlant(
                plant.getId(),
                plant.getPlantSpecies().getId(),
                plant.getGrowingLocation().getId(),
                plant.getPlantingDate(),
                plant.getGerminationDate(),
                plant.getComment()
        )).thenReturn(plant);

        String plantJson = "{\"plantSpecies\":{\"id\":%d},\"growingLocation\":{\"id\":%d},\"plantingDate\":\"%s\",\"germinationDate\":\"%s\",\"comment\":\"%s\"}".formatted(
                plant.getPlantSpecies().getId(),
                plant.getGrowingLocation().getId(),
                plant.getPlantingDate().toString(),
                plant.getGerminationDate().toString(),
                plant.getComment()
        );

        mockMvc.perform(put("/api/plants/{id}", plant.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(plantJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(plant.getId().intValue())))
                .andExpect(jsonPath("$.plantSpecies.id", is(plantSpecies.getId().intValue())))
                .andExpect(jsonPath("$.growingLocation.id", is(growingLocation.getId().intValue())))
                .andExpect(jsonPath("$.plantingDate", is(plant.getPlantingDate().toString())))
                .andExpect(jsonPath("$.germinationDate", is(plant.getGerminationDate().toString())))
                .andExpect(jsonPath("$.comment", is(plant.getComment())));
    }

    @Test
    public void testDeletePlant() throws Exception {
        Mockito.doNothing().when(plantService).deletePlant(plant.getId());

        mockMvc.perform(delete("/api/plants/{id}", plant.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }
}