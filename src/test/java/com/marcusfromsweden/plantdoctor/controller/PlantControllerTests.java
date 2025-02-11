package com.marcusfromsweden.plantdoctor.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.marcusfromsweden.plantdoctor.dto.GrowingLocationDTO;
import com.marcusfromsweden.plantdoctor.dto.PlantDTO;
import com.marcusfromsweden.plantdoctor.dto.SeedPackageDTO;
import com.marcusfromsweden.plantdoctor.dto.mapper.GrowingLocationMapper;
import com.marcusfromsweden.plantdoctor.service.PlantCommentService;
import com.marcusfromsweden.plantdoctor.service.PlantService;
import com.marcusfromsweden.plantdoctor.util.GrowingLocationTestHelper;
import com.marcusfromsweden.plantdoctor.util.PlantTestHelper;
import com.marcusfromsweden.plantdoctor.util.SeedPackageTestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
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
@Import({PlantTestHelper.class, SeedPackageTestHelper.class, GrowingLocationTestHelper.class, GrowingLocationMapper.class})
public class PlantControllerTests {

    private static final String API_PATH = "/api/plants";

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private SeedPackageTestHelper seedPackageTestHelper;
    @Autowired
    private GrowingLocationTestHelper growingLocationTestHelper;
    @Autowired
    private PlantTestHelper plantTestHelper;

    @MockBean
    private PlantService plantService;
    @MockBean
    @SuppressWarnings("unused")
    private PlantCommentService plantCommentService;

    private PlantDTO plantDTO;
    private SeedPackageDTO seedPackageDTO;
    private GrowingLocationDTO growingLocationDTO;

    @BeforeEach
    public void setup() {
        seedPackageDTO = seedPackageTestHelper.createDTO(1L,
                                                         "Some seeds",
                                                         1L,
                                                         10);
        growingLocationDTO = growingLocationTestHelper.createDTO(1L,
                                                                 "Clay pot nbr 1");
        plantDTO = plantTestHelper.createDTO(1L,
                                             seedPackageDTO.id(), growingLocationDTO.id(), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 1, 15));
    }

    @Test
    public void shouldGetAllPlants() throws Exception {
        Mockito.when(plantService.getAllPlants()).thenReturn(Collections.singletonList(plantDTO));

        mockMvc.perform(get(API_PATH)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(plantDTO.id().intValue())))
                .andExpect(jsonPath("$[0].seedPackageId", is(seedPackageDTO.id().intValue())))
                .andExpect(jsonPath("$[0].growingLocationId", is(growingLocationDTO.id().intValue())))
                .andExpect(jsonPath("$[0].plantingDate", is(plantDTO.plantingDate().toString())))
                .andExpect(jsonPath("$[0].germinationDate", is(plantDTO.germinationDate().toString())));

        Mockito.verify(plantService, Mockito.times(1)).getAllPlants();
    }

    @Test
    public void shouldGetPlantById() throws Exception {
        Mockito.when(plantService.getPlantById(plantDTO.id())).thenReturn(Optional.of(plantDTO));

        mockMvc.perform(get(API_PATH + "/{id}", plantDTO.id())
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(plantDTO.id().intValue())))
                .andExpect(jsonPath("$.seedPackageId", is(seedPackageDTO.id().intValue())))
                .andExpect(jsonPath("$.growingLocationId", is(growingLocationDTO.id().intValue())))
                .andExpect(jsonPath("$.plantingDate", is(plantDTO.plantingDate().toString())))
                .andExpect(jsonPath("$.germinationDate", is(plantDTO.germinationDate().toString())));

        Mockito.verify(plantService, Mockito.times(1))
                .getPlantById(plantDTO.id());
    }

    @Test
    public void shouldCreatePlant() throws Exception {
        Mockito.when(plantService.createPlant(Mockito.any(PlantDTO.class)))
                .thenReturn(plantDTO);
        String plantJson = objectMapper.writeValueAsString(plantDTO);

        mockMvc.perform(post(API_PATH)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(plantJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(plantDTO.id().intValue())))
                .andExpect(jsonPath("$.seedPackageId", is(seedPackageDTO.id().intValue())))
                .andExpect(jsonPath("$.growingLocationId", is(growingLocationDTO.id().intValue())))
                .andExpect(jsonPath("$.plantingDate", is(plantDTO.plantingDate().toString())))
                .andExpect(jsonPath("$.germinationDate", is(plantDTO.germinationDate().toString())));

        Mockito.verify(plantService, Mockito.times(1))
                .createPlant(Mockito.any(PlantDTO.class));
    }

    @Test
    public void shouldUpdatePlant() throws Exception {
        Mockito.when(plantService.updatePlant(plantDTO.id(), plantDTO)).thenReturn(plantDTO);
        String plantJson = objectMapper.writeValueAsString(plantDTO);

        //todo create a new plantDTO for the update
        mockMvc.perform(put(API_PATH + "/{id}", plantDTO.id())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(plantJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(plantDTO.id().intValue())))
                .andExpect(jsonPath("$.seedPackageId", is(seedPackageDTO.id().intValue())))
                .andExpect(jsonPath("$.growingLocationId", is(growingLocationDTO.id().intValue())))
                .andExpect(jsonPath("$.plantingDate", is(plantDTO.plantingDate().toString())))
                .andExpect(jsonPath("$.germinationDate", is(plantDTO.germinationDate().toString())));

        Mockito.verify(plantService, Mockito.times(1))
                .updatePlant(plantDTO.id(), plantDTO);
    }

    @Test
    public void shouldDeletePlant() throws Exception {
        Mockito.doNothing().when(plantService).deletePlant(plantDTO.id());

        mockMvc.perform(delete(API_PATH + "/{id}", plantDTO.id())
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        Mockito.verify(plantService, Mockito.times(1))
                .deletePlant(plantDTO.id());
    }
}