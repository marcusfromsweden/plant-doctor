package com.marcusfromsweden.plantdoctor.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.marcusfromsweden.plantdoctor.dto.BotanicalSpeciesDTO;
import com.marcusfromsweden.plantdoctor.service.BotanicalSpeciesService;
import com.marcusfromsweden.plantdoctor.util.BotanicalSpeciesTestHelper;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(BotanicalSpeciesController.class)
@AutoConfigureMockMvc
@Import(BotanicalSpeciesTestHelper.class)
public class BotanicalSpeciesControllerTests {

    private static final String API_PATH_PLANT_SPECIES = "/api/botanical-species";
    private static final long EXISTING_BOTANICAL_SPECIES_ID = 1L;
    private static final String EXISTING_BOTANICAL_SPECIES_LATIN_NAME = "tametoe talacum";
    private static final String EXISTING_BOTANICAL_SPECIES_DESCRIPTION = "A tasty treat";
    private static final int EXISTING_BOTANICAL_SPECIES_ESTIMATED_DAYS_TO_GERMINATION = 7;
    private static final Long UPDATED_BOTANICAL_SPECIES_ID = 2L;
    private static final String UPDATED_BOTANICAL_SPECIES_LATIN_NAME = "tometoe silucum";
    private static final String UPDATED_BOTANICAL_SPECIES_DESCRIPTION = "A tasty treat as well";
    private static final Integer UPDATED_BOTANICAL_SPECIES_ESTIMATED_DAYS_TO_GERMINATION = 11;

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private BotanicalSpeciesTestHelper botanicalSpeciesTestHelper;

    @MockitoBean
    private BotanicalSpeciesService botanicalSpeciesService;

    private BotanicalSpeciesDTO existingBotanicalSpeciesDTO;
    private BotanicalSpeciesDTO updatedBotanicalSpeciesDTO;

    @BeforeEach
    public void setupTest() {
        existingBotanicalSpeciesDTO = botanicalSpeciesTestHelper.createDTO(
                EXISTING_BOTANICAL_SPECIES_ID,
                EXISTING_BOTANICAL_SPECIES_LATIN_NAME,
                EXISTING_BOTANICAL_SPECIES_DESCRIPTION,
                EXISTING_BOTANICAL_SPECIES_ESTIMATED_DAYS_TO_GERMINATION
        );

        updatedBotanicalSpeciesDTO = botanicalSpeciesTestHelper.createDTO(
                UPDATED_BOTANICAL_SPECIES_ID,
                UPDATED_BOTANICAL_SPECIES_LATIN_NAME,
                UPDATED_BOTANICAL_SPECIES_DESCRIPTION,
                UPDATED_BOTANICAL_SPECIES_ESTIMATED_DAYS_TO_GERMINATION
        );
    }

    @Test
    public void shouldGetAllBotanicalSpecies() throws Exception {
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
    public void shouldGetBotanicalSpeciesById() throws Exception {
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
    public void shouldCreateBotanicalSpecies() throws Exception {
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
    public void shouldUpdateBotanicalSpecies() throws Exception {
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
    public void shouldGetBotanicalSpeciesByName() throws Exception {
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
    public void shouldDeleteBotanicalSpecies() throws Exception {
        Mockito.doNothing().when(botanicalSpeciesService).deleteBotanicalSpecies(existingBotanicalSpeciesDTO.id());

        mockMvc.perform(delete(API_PATH_PLANT_SPECIES + "/{id}", existingBotanicalSpeciesDTO.id())
                                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isNoContent());

        Mockito.verify(botanicalSpeciesService, Mockito.times(1))
                .deleteBotanicalSpecies(existingBotanicalSpeciesDTO.id());
    }
}