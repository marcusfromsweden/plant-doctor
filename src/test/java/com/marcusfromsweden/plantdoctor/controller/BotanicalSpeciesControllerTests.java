package com.marcusfromsweden.plantdoctor.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
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

    private static final String API_PATH_PLANT_SPECIES = "/api/plant-species";
    public static final long EXISTING_BOTANICAL_SPECIES_ID = 1L;
    public static final String EXISTING_BOTANICAL_SPECIES_LATIN_NAME = "tametoe talacum";
    public static final String EXISTING_BOTANICAL_SPECIES_DESCRIPTION = "A tasty treat";
    public static final int EXISTING_BOTANICAL_SPECIES_ESTIMATED_DAYS_TO_GERMINATION = 7;
    private static final Long UPDATED_BOTANICAL_SPECIES_ID = 2L;
    private static final String UPDATED_BOTANICAL_SPECIES_LATIN_NAME = "tometoe silucum";
    private static final String UPDATED_BOTANICAL_SPECIES_DESCRIPTION = "A tasty treat as well";
    private static final Integer UPDATED_BOTANICAL_SPECIES_ESTIMATED_DAYS_TO_GERMINATION = 11;

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private BotanicalSpeciesService botanicalSpeciesService;

    private BotanicalSpeciesDTO existingBotanicalSpeciesDTO;
    private BotanicalSpeciesDTO updatedBotanicalSpeciesDTO;

    @BeforeEach
    public void setup() {
        existingBotanicalSpeciesDTO = BotanicalSpeciesDTO.builder()
                .id(EXISTING_BOTANICAL_SPECIES_ID)
                .latinName(EXISTING_BOTANICAL_SPECIES_LATIN_NAME)
                .description(EXISTING_BOTANICAL_SPECIES_DESCRIPTION)
                .estimatedDaysToGermination(EXISTING_BOTANICAL_SPECIES_ESTIMATED_DAYS_TO_GERMINATION)
                .build();

        updatedBotanicalSpeciesDTO = BotanicalSpeciesDTO.builder()
                .id(UPDATED_BOTANICAL_SPECIES_ID)
                .latinName(UPDATED_BOTANICAL_SPECIES_LATIN_NAME)
                .description(UPDATED_BOTANICAL_SPECIES_DESCRIPTION)
                .estimatedDaysToGermination(UPDATED_BOTANICAL_SPECIES_ESTIMATED_DAYS_TO_GERMINATION)
                .build();
    }

    @Test
    public void testGetAllBotanicalSpecies() throws Exception {
        Mockito.when(botanicalSpeciesService.getAllBotanicalSpecies())
                .thenReturn(Collections.singletonList(existingBotanicalSpeciesDTO));

        mockMvc.perform(get(API_PATH_PLANT_SPECIES).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(existingBotanicalSpeciesDTO.id().intValue())))
                .andExpect(jsonPath("$[0].latinName", is(existingBotanicalSpeciesDTO.latinName())))
                .andExpect(jsonPath("$[0].description", is(existingBotanicalSpeciesDTO.description())))
                .andExpect(jsonPath("$[0].estimatedDaysToGermination", is(existingBotanicalSpeciesDTO.estimatedDaysToGermination())));

        Mockito.verify(botanicalSpeciesService, Mockito.times(1)).getAllBotanicalSpecies();
    }

    @Test
    public void testGetBotanicalSpeciesById() throws Exception {
        Mockito.when(botanicalSpeciesService.getBotanicalSpeciesById(existingBotanicalSpeciesDTO.id()))
                .thenReturn(Optional.of(existingBotanicalSpeciesDTO));

        mockMvc.perform(get(API_PATH_PLANT_SPECIES + "/{id}", existingBotanicalSpeciesDTO.id())
                                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(existingBotanicalSpeciesDTO.id().intValue())))
                .andExpect(jsonPath("$.latinName", is(existingBotanicalSpeciesDTO.latinName())))
                .andExpect(jsonPath("$.description", is(existingBotanicalSpeciesDTO.description())))
                .andExpect(jsonPath("$.estimatedDaysToGermination", is(existingBotanicalSpeciesDTO.estimatedDaysToGermination())));

        Mockito.verify(botanicalSpeciesService, Mockito.times(1))
                .getBotanicalSpeciesById(existingBotanicalSpeciesDTO.id());
    }

    @Test
    public void testCreateBotanicalSpecies() throws Exception {
        Mockito.when(botanicalSpeciesService.createBotanicalSpecies(Mockito.any(BotanicalSpeciesDTO.class)))
                .thenReturn(existingBotanicalSpeciesDTO);
        String botanicalSpeciesJson = objectMapper.writeValueAsString(existingBotanicalSpeciesDTO);

        mockMvc.perform(post(API_PATH_PLANT_SPECIES)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(botanicalSpeciesJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(existingBotanicalSpeciesDTO.id().intValue())))
                .andExpect(jsonPath("$.latinName", is(existingBotanicalSpeciesDTO.latinName())))
                .andExpect(jsonPath("$.description", is(existingBotanicalSpeciesDTO.description())))
                .andExpect(jsonPath("$.estimatedDaysToGermination", is(existingBotanicalSpeciesDTO.estimatedDaysToGermination())));

        Mockito.verify(botanicalSpeciesService, Mockito.times(1))
                .createBotanicalSpecies(Mockito.any(BotanicalSpeciesDTO.class));
    }

    @Test
    public void testUpdateBotanicalSpecies() throws Exception {
        Mockito
                .when(botanicalSpeciesService.updateBotanicalSpecies(Mockito.eq(existingBotanicalSpeciesDTO.id()),
                                                                     Mockito.any(BotanicalSpeciesDTO.class)))
                .thenReturn(updatedBotanicalSpeciesDTO);
        //todo use another DTO for the update
        String botanicalSpeciesJson = objectMapper.writeValueAsString(updatedBotanicalSpeciesDTO);

        mockMvc.perform(put(API_PATH_PLANT_SPECIES + "/{id}", existingBotanicalSpeciesDTO.id())
                                .contentType(MediaType.APPLICATION_JSON).content(botanicalSpeciesJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(updatedBotanicalSpeciesDTO.id().intValue())))
                .andExpect(jsonPath("$.latinName", is(updatedBotanicalSpeciesDTO.latinName())))
                .andExpect(jsonPath("$.description", is(updatedBotanicalSpeciesDTO.description())))
                .andExpect(jsonPath("$.estimatedDaysToGermination", is(updatedBotanicalSpeciesDTO.estimatedDaysToGermination())));

        Mockito.verify(botanicalSpeciesService, Mockito.times(1))
                .updateBotanicalSpecies(
                        Mockito.eq(existingBotanicalSpeciesDTO.id()),
                        Mockito.any(BotanicalSpeciesDTO.class));
    }

    @Test
    public void testGetBotanicalSpeciesByName() throws Exception {
        String speciesName = "Tomato";
        Mockito.when(botanicalSpeciesService.getBotanicalSpeciesByLatinName(speciesName))
                .thenReturn(Optional.of(existingBotanicalSpeciesDTO));

        mockMvc.perform(get(API_PATH_PLANT_SPECIES + "/name/{name}", speciesName)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(existingBotanicalSpeciesDTO.id().intValue())))
                .andExpect(jsonPath("$.latinName", is(existingBotanicalSpeciesDTO.latinName())))
                .andExpect(jsonPath("$.description", is(existingBotanicalSpeciesDTO.description())))
                .andExpect(jsonPath("$.estimatedDaysToGermination", is(existingBotanicalSpeciesDTO.estimatedDaysToGermination())));

        Mockito.verify(botanicalSpeciesService, Mockito.times(1))
                .getBotanicalSpeciesByLatinName(speciesName);
    }

    @Test
    public void testDeleteBotanicalSpecies() throws Exception {
        Mockito.doNothing().when(botanicalSpeciesService).deleteBotanicalSpecies(existingBotanicalSpeciesDTO.id());

        mockMvc.perform(delete(API_PATH_PLANT_SPECIES + "/{id}", existingBotanicalSpeciesDTO.id())
                                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isNoContent());

        Mockito.verify(botanicalSpeciesService, Mockito.times(1))
                .deleteBotanicalSpecies(existingBotanicalSpeciesDTO.id());
    }
}