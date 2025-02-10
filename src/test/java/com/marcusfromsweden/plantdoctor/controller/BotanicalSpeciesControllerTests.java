package com.marcusfromsweden.plantdoctor.controller;

import com.marcusfromsweden.plantdoctor.dto.BotanicalSpeciesDTO;
import com.marcusfromsweden.plantdoctor.service.BotanicalSpeciesService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
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


@WebMvcTest(BotanicalSpeciesController.class)
@AutoConfigureMockMvc
public class BotanicalSpeciesControllerTests {

    public static final String API_PATH_PLANT_SPECIES = "/api/plant-species";
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BotanicalSpeciesService botanicalSpeciesService;

    private BotanicalSpeciesDTO botanicalSpeciesDTO;

    @BeforeEach
    public void setup() {
        botanicalSpeciesDTO = BotanicalSpeciesDTO.builder()
                .id(1L)
                .latinName("Tomato")
                .description("A tasty treat")
                .estimatedDaysToGermination(7)
                .build();
    }

    @Test
    public void testGetAllBotanicalSpecies() throws Exception {
        Mockito.when(botanicalSpeciesService.getAllBotanicalSpecies())
                .thenReturn(Collections.singletonList(botanicalSpeciesDTO));

        mockMvc.perform(get(API_PATH_PLANT_SPECIES).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(botanicalSpeciesDTO.id().intValue())))
                .andExpect(jsonPath("$[0].latinName", is(botanicalSpeciesDTO.latinName())))
                .andExpect(jsonPath("$[0].description", is(botanicalSpeciesDTO.description())))
                .andExpect(jsonPath("$[0].estimatedDaysToGermination", is(botanicalSpeciesDTO.estimatedDaysToGermination())));
    }

    @Test
    public void testGetBotanicalSpeciesById() throws Exception {
        Mockito.when(botanicalSpeciesService.getBotanicalSpeciesById(botanicalSpeciesDTO.id()))
                .thenReturn(Optional.of(botanicalSpeciesDTO));

        mockMvc.perform(get(API_PATH_PLANT_SPECIES + "/{id}", botanicalSpeciesDTO.id())
                                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(botanicalSpeciesDTO.id().intValue())))
                .andExpect(jsonPath("$.latinName", is(botanicalSpeciesDTO.latinName())))
                .andExpect(jsonPath("$.description", is(botanicalSpeciesDTO.description())))
                .andExpect(jsonPath("$.estimatedDaysToGermination", is(botanicalSpeciesDTO.estimatedDaysToGermination())));
    }

    @Test
    public void testCreateBotanicalSpecies() throws Exception {
        Mockito
                .when(botanicalSpeciesService.createBotanicalSpecies(Mockito.any(BotanicalSpeciesDTO.class)))
                .thenReturn(botanicalSpeciesDTO);

        String botanicalSpeciesJson = "{\"latinName\":\"%s\",\"description\":\"%s\",\"estimatedDaysToGermination\":%d}"
                .formatted(botanicalSpeciesDTO.latinName(), botanicalSpeciesDTO.description(), botanicalSpeciesDTO.estimatedDaysToGermination());

        mockMvc.perform(post(API_PATH_PLANT_SPECIES)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(botanicalSpeciesJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(botanicalSpeciesDTO.id().intValue())))
                .andExpect(jsonPath("$.latinName", is(botanicalSpeciesDTO.latinName())))
                .andExpect(jsonPath("$.description", is(botanicalSpeciesDTO.description())))
                .andExpect(jsonPath("$.estimatedDaysToGermination", is(botanicalSpeciesDTO.estimatedDaysToGermination())));
    }

    @Test
    public void testUpdateBotanicalSpecies() throws Exception {
        Mockito
                .when(botanicalSpeciesService.updateBotanicalSpecies(Mockito.eq(botanicalSpeciesDTO.id()),
                                                                     Mockito.any(BotanicalSpeciesDTO.class)))
                .thenReturn(botanicalSpeciesDTO);

        String botanicalSpeciesJson = "{\"latinName\":\"%s\",\"description\":\"%s\",\"estimatedDaysToGermination\":%d}"
                .formatted(botanicalSpeciesDTO.latinName(), botanicalSpeciesDTO.description(), botanicalSpeciesDTO.estimatedDaysToGermination());

        mockMvc.perform(put(API_PATH_PLANT_SPECIES + "/{id}", botanicalSpeciesDTO.id())
                                .contentType(MediaType.APPLICATION_JSON).content(botanicalSpeciesJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(botanicalSpeciesDTO.id().intValue())))
                .andExpect(jsonPath("$.latinName", is(botanicalSpeciesDTO.latinName())))
                .andExpect(jsonPath("$.description", is(botanicalSpeciesDTO.description())))
                .andExpect(jsonPath("$.estimatedDaysToGermination", is(botanicalSpeciesDTO.estimatedDaysToGermination())));
    }

    @Test
    public void testGetBotanicalSpeciesByName() throws Exception {
        String speciesName = "Tomato";
        Mockito.when(botanicalSpeciesService.getBotanicalSpeciesByLatinName(speciesName))
                .thenReturn(Optional.of(botanicalSpeciesDTO));

        mockMvc.perform(get(API_PATH_PLANT_SPECIES + "/name/{name}", speciesName)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(botanicalSpeciesDTO.id().intValue())))
                .andExpect(jsonPath("$.latinName", is(botanicalSpeciesDTO.latinName())))
                .andExpect(jsonPath("$.description", is(botanicalSpeciesDTO.description())))
                .andExpect(jsonPath("$.estimatedDaysToGermination", is(botanicalSpeciesDTO.estimatedDaysToGermination())));
    }

    @Test
    public void testDeleteBotanicalSpecies() throws Exception {
        Mockito.doNothing().when(botanicalSpeciesService).deleteBotanicalSpecies(botanicalSpeciesDTO.id());

        mockMvc.perform(delete(API_PATH_PLANT_SPECIES + "/{id}", botanicalSpeciesDTO.id())
                                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isNoContent());
    }
}