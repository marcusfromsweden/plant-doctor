package com.marcusfromsweden.plantdoctor.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.marcusfromsweden.plantdoctor.dto.SeedPackageDTO;
import com.marcusfromsweden.plantdoctor.service.SeedPackageService;
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

import java.util.Collections;
import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(SeedPackageController.class)
@AutoConfigureMockMvc
@Import(SeedPackageTestHelper.class)
public class SeedPackageControllerTests {

    private static final String API_PATH_SEED_PACKAGES = "/api/seed-packages";
    private static final long EXISTING_SEED_PACKAGE_ID = 1L;
    private static final long EXISTING_SEED_PACKAGE_BOTANICAL_SPECIES_ID = 15L;
    private static final String EXISTING_SEED_PACKAGE_NAME = "Tomato Seeds";
    private static final int EXISTING_SEED_PACKAGE_NUMBER_OF_SEEDS = 100;
    private static final long UPDATED_SEED_PACKAGE_ID = 2L;
    private static final long UPDATED_SEED_PACKAGE_BOTANICAL_SPECIES_ID = 22L;
    private static final String UPDATED_SEED_PACKAGE_NAME = "Tomato Seeds Ver 2";
    private static final int UPDATED_SEED_PACKAGE_NUMBER_OF_SEEDS = 200;

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private SeedPackageTestHelper seedPackageTestHelper;

    @MockBean
    private SeedPackageService seedPackageService;

    private SeedPackageDTO existingSeedPackageDTO;
    private SeedPackageDTO updatedSeedPackageDTO;

    @BeforeEach
    public void setup() {
        existingSeedPackageDTO = seedPackageTestHelper.createDTO(
                EXISTING_SEED_PACKAGE_ID,
                EXISTING_SEED_PACKAGE_NAME,
                EXISTING_SEED_PACKAGE_BOTANICAL_SPECIES_ID,
                EXISTING_SEED_PACKAGE_NUMBER_OF_SEEDS
        );

        updatedSeedPackageDTO = seedPackageTestHelper.createDTO(
                UPDATED_SEED_PACKAGE_ID,
                UPDATED_SEED_PACKAGE_NAME,
                UPDATED_SEED_PACKAGE_BOTANICAL_SPECIES_ID,
                UPDATED_SEED_PACKAGE_NUMBER_OF_SEEDS
        );
    }

    @Test
    public void shouldGetAllSeedPackages() throws Exception {
        Mockito.when(seedPackageService.getAllSeedPackages())
                .thenReturn(Collections.singletonList(existingSeedPackageDTO));

        mockMvc.perform(get(API_PATH_SEED_PACKAGES).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(existingSeedPackageDTO.id().intValue())))
                .andExpect(jsonPath("$[0].botanicalSpeciesId", is(existingSeedPackageDTO.botanicalSpeciesId().intValue())))
                .andExpect(jsonPath("$[0].name", is(existingSeedPackageDTO.name())))
                .andExpect(jsonPath("$[0].numberOfSeeds", is(existingSeedPackageDTO.numberOfSeeds())));

        Mockito.verify(seedPackageService, Mockito.times(1)).getAllSeedPackages();
    }

    @Test
    public void shouldGetSeedPackageById() throws Exception {
        Mockito.when(seedPackageService.getSeedPackageById(existingSeedPackageDTO.id()))
                .thenReturn(Optional.of(existingSeedPackageDTO));

        mockMvc.perform(get(API_PATH_SEED_PACKAGES + "/{id}", existingSeedPackageDTO.id())
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(existingSeedPackageDTO.id().intValue())))
                .andExpect(jsonPath("$.botanicalSpeciesId", is(existingSeedPackageDTO.botanicalSpeciesId().intValue())))
                .andExpect(jsonPath("$.name", is(existingSeedPackageDTO.name())))
                .andExpect(jsonPath("$.numberOfSeeds", is(existingSeedPackageDTO.numberOfSeeds())));

        Mockito.verify(seedPackageService, Mockito.times(1))
                .getSeedPackageById(existingSeedPackageDTO.id());
    }

    @Test
    public void shouldCreateSeedPackage() throws Exception {
        Mockito.when(seedPackageService.createSeedPackage(Mockito.any(SeedPackageDTO.class)))
                .thenReturn(existingSeedPackageDTO);
        String seedPackageJson = objectMapper.writeValueAsString(existingSeedPackageDTO);

        mockMvc.perform(post(API_PATH_SEED_PACKAGES)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(seedPackageJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(existingSeedPackageDTO.id().intValue())))
                .andExpect(jsonPath("$.botanicalSpeciesId", is(existingSeedPackageDTO.botanicalSpeciesId().intValue())))
                .andExpect(jsonPath("$.name", is(existingSeedPackageDTO.name())))
                .andExpect(jsonPath("$.numberOfSeeds", is(existingSeedPackageDTO.numberOfSeeds())));

        Mockito.verify(seedPackageService, Mockito.times(1))
                .createSeedPackage(Mockito.any(SeedPackageDTO.class));
    }

    @Test
    public void shouldUpdateSeedPackage() throws Exception {
        Mockito.when(seedPackageService.updateSeedPackage(Mockito.eq(existingSeedPackageDTO.id()),
                                                          Mockito.any(SeedPackageDTO.class)))
                .thenReturn(updatedSeedPackageDTO);

        String updatedSeedPackageJson = objectMapper.writeValueAsString(updatedSeedPackageDTO);

        mockMvc.perform(put(API_PATH_SEED_PACKAGES + "/{id}", existingSeedPackageDTO.id())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(updatedSeedPackageJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(updatedSeedPackageDTO.id().intValue())))
                .andExpect(jsonPath("$.botanicalSpeciesId", is(updatedSeedPackageDTO.botanicalSpeciesId().intValue())))
                .andExpect(jsonPath("$.name", is(updatedSeedPackageDTO.name())))
                .andExpect(jsonPath("$.numberOfSeeds", is(updatedSeedPackageDTO.numberOfSeeds())));

        Mockito.verify(seedPackageService, Mockito.times(1))
                .updateSeedPackage(Mockito.eq(existingSeedPackageDTO.id()),
                                   Mockito.any(SeedPackageDTO.class));
    }

    @Test
    public void shouldDeleteSeedPackage() throws Exception {
        Mockito.doNothing().when(seedPackageService).deleteSeedPackage(existingSeedPackageDTO.id());

        mockMvc.perform(delete(API_PATH_SEED_PACKAGES + "/{id}", existingSeedPackageDTO.id())
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        Mockito.verify(seedPackageService, Mockito.times(1))
                .deleteSeedPackage(existingSeedPackageDTO.id());
    }
}