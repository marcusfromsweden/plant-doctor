package com.marcusfromsweden.plantdoctor.controller;

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

    public static final String API_PATH_SEED_PACKAGES = "/api/seed-packages";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SeedPackageService seedPackageService;

    private SeedPackageDTO seedPackageDTO;

    @BeforeEach
    public void setup() {
        seedPackageDTO = SeedPackageDTO.builder()
                .id(1L)
                .botanicalSpeciesId(15L)
                .name("Tomato Seeds")
                .numberOfSeeds(100)
                .build();
    }

    @Test
    public void testGetAllSeedPackages() throws Exception {
        Mockito.when(seedPackageService.getAllSeedPackages())
                .thenReturn(Collections.singletonList(seedPackageDTO));

        mockMvc.perform(get(API_PATH_SEED_PACKAGES).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(seedPackageDTO.id().intValue())))
                .andExpect(jsonPath("$[0].botanicalSpeciesId", is(seedPackageDTO.botanicalSpeciesId().intValue())))
                .andExpect(jsonPath("$[0].name", is(seedPackageDTO.name())))
                .andExpect(jsonPath("$[0].numberOfSeeds", is(seedPackageDTO.numberOfSeeds())));
    }

    @Test
    public void testGetSeedPackageById() throws Exception {
        Mockito.when(seedPackageService.getSeedPackageById(seedPackageDTO.id()))
                .thenReturn(Optional.of(seedPackageDTO));

        mockMvc.perform(get(API_PATH_SEED_PACKAGES + "/{id}", seedPackageDTO.id())
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(seedPackageDTO.id().intValue())))
                .andExpect(jsonPath("$.botanicalSpeciesId", is(seedPackageDTO.botanicalSpeciesId().intValue())))
                .andExpect(jsonPath("$.name", is(seedPackageDTO.name())))
                .andExpect(jsonPath("$.numberOfSeeds", is(seedPackageDTO.numberOfSeeds())));
    }

    @Test
    public void testCreateSeedPackage() throws Exception {
        Mockito.when(seedPackageService.createSeedPackage(Mockito.any(SeedPackageDTO.class)))
                .thenReturn(seedPackageDTO);

        String seedPackageJson = "{\"botanicalSpeciesId\":%d,\"name\":\"%s\",\"numberOfSeeds\":%d}"
                .formatted(seedPackageDTO.botanicalSpeciesId(), seedPackageDTO.name(), seedPackageDTO.numberOfSeeds());

        mockMvc.perform(post(API_PATH_SEED_PACKAGES)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(seedPackageJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(seedPackageDTO.id().intValue())))
                .andExpect(jsonPath("$.botanicalSpeciesId", is(seedPackageDTO.botanicalSpeciesId().intValue())))
                .andExpect(jsonPath("$.name", is(seedPackageDTO.name())))
                .andExpect(jsonPath("$.numberOfSeeds", is(seedPackageDTO.numberOfSeeds())));
    }

    @Test
    public void testUpdateSeedPackage() throws Exception {
        Mockito.when(seedPackageService.updateSeedPackage(Mockito.eq(seedPackageDTO.id()), Mockito.any(SeedPackageDTO.class)))
                .thenReturn(seedPackageDTO);

        String seedPackageJson = "{\"botanicalSpeciesId\":%d,\"name\":\"%s\",\"numberOfSeeds\":%d}"
                .formatted(seedPackageDTO.botanicalSpeciesId(), seedPackageDTO.name(), seedPackageDTO.numberOfSeeds());

        mockMvc.perform(put(API_PATH_SEED_PACKAGES + "/{id}", seedPackageDTO.id())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(seedPackageJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(seedPackageDTO.id().intValue())))
                .andExpect(jsonPath("$.botanicalSpeciesId", is(seedPackageDTO.botanicalSpeciesId().intValue())))
                .andExpect(jsonPath("$.name", is(seedPackageDTO.name())))
                .andExpect(jsonPath("$.numberOfSeeds", is(seedPackageDTO.numberOfSeeds())));
    }

    @Test
    public void testDeleteSeedPackage() throws Exception {
        Mockito.doNothing().when(seedPackageService).deleteSeedPackage(seedPackageDTO.id());

        mockMvc.perform(delete(API_PATH_SEED_PACKAGES + "/{id}", seedPackageDTO.id())
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }
}