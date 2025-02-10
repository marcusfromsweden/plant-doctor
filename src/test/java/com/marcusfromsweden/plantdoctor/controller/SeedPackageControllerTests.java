package com.marcusfromsweden.plantdoctor.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.marcusfromsweden.plantdoctor.dto.SeedPackageDTO;
import com.marcusfromsweden.plantdoctor.service.SeedPackageService;
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

@WebMvcTest(SeedPackageController.class)
@AutoConfigureMockMvc
public class SeedPackageControllerTests {

    private static final String API_PATH_SEED_PACKAGES = "/api/seed-packages";

    public static final long EXISTING_SEED_PACKAGE_ID = 1L;
    public static final long EXISTING_SEED_PACKAGE_BOTANICAL_SPECIES_ID = 15L;
    public static final String EXISTING_SEED_PACKAGE_NAME = "Tomato Seeds";
    public static final int EXISTING_SEED_PACKAGE_NUMBER_OF_SEEDS = 100;
    public static final long UPDATED_SEED_PACKAGE_ID = 2L;
    public static final long UPDATED_SEED_PACKAGE_BOTANICAL_SPECIES_ID = 22L;
    public static final String UPDATED_SEED_PACKAGE_NAME = "Tomato Seeds Ver 2";
    public static final int UPDATED_SEED_PACKAGE_NUMBER_OF_SEEDS = 200;

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private SeedPackageService seedPackageService;

    private SeedPackageDTO existingSeedPackageDTO;
    private SeedPackageDTO updatedSeedPackageDTO;

    @BeforeEach
    public void setup() {
        existingSeedPackageDTO = SeedPackageDTO.builder()
                .id(EXISTING_SEED_PACKAGE_ID)
                .botanicalSpeciesId(EXISTING_SEED_PACKAGE_BOTANICAL_SPECIES_ID)
                .name(EXISTING_SEED_PACKAGE_NAME)
                .numberOfSeeds(EXISTING_SEED_PACKAGE_NUMBER_OF_SEEDS)
                .build();

        updatedSeedPackageDTO = SeedPackageDTO.builder()
                .id(UPDATED_SEED_PACKAGE_ID)
                .botanicalSpeciesId(UPDATED_SEED_PACKAGE_BOTANICAL_SPECIES_ID)
                .name(UPDATED_SEED_PACKAGE_NAME)
                .numberOfSeeds(UPDATED_SEED_PACKAGE_NUMBER_OF_SEEDS)
                .build();
    }

    @Test
    public void testGetAllSeedPackages() throws Exception {
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
    public void testGetSeedPackageById() throws Exception {
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
    public void testCreateSeedPackage() throws Exception {
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
    public void testUpdateSeedPackage() throws Exception {
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
    public void testDeleteSeedPackage() throws Exception {
        Mockito.doNothing().when(seedPackageService).deleteSeedPackage(existingSeedPackageDTO.id());

        mockMvc.perform(delete(API_PATH_SEED_PACKAGES + "/{id}", existingSeedPackageDTO.id())
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        Mockito.verify(seedPackageService, Mockito.times(1))
                .deleteSeedPackage(existingSeedPackageDTO.id());
    }
}