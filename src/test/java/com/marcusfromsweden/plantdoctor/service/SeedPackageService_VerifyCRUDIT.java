package com.marcusfromsweden.plantdoctor.service;

import com.marcusfromsweden.plantdoctor.dto.BotanicalSpeciesDTO;
import com.marcusfromsweden.plantdoctor.dto.SeedPackageDTO;
import com.marcusfromsweden.plantdoctor.entity.SeedPackage;
import com.marcusfromsweden.plantdoctor.util.PostgresTestContainerTest;
import com.marcusfromsweden.plantdoctor.util.RepositoryTestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SeedPackageService_VerifyCRUDIT extends PostgresTestContainerTest {

    public static final String BOTANICAL_SPECIES_1_LATIN_NAME = "Botanical Species 1";
    public static final String BOTANICAL_SPECIES_1_DESCRIPTION = "Some description";
    public static final int BOTANICAL_SPECIES_1_ESTIMATED_DAYS_TO_GERMINATION = 7;
    public static final String SEED_PACKAGE_1_NAME = "Some seed pack 1";
    public static final int SEED_PACKAGE_1_NUMBER_OF_SEEDS = 100;
    public static final String SEED_PACKAGE_1_NAME_UPDATED = "SP 2";
    public static final int SEED_PACKAGE_1_NUMBER_OF_SEEDS_UPDATED = 200;
    @Autowired
    private SeedPackageService seedPackageService;
    @Autowired
    private BotanicalSpeciesService botanicalSpeciesService;
    @Autowired
    private RepositoryTestHelper repositoryTestHelper;

    @BeforeEach
    public void setUp() {
        repositoryTestHelper.deleteAllData();
    }

    //todo create constants for tests values

    @Test
    public void testCreateAndUpdate() {
        BotanicalSpeciesDTO botanicalSpeciesDTO = BotanicalSpeciesDTO.builder()
                .latinName(BOTANICAL_SPECIES_1_LATIN_NAME)
                .description(BOTANICAL_SPECIES_1_DESCRIPTION)
                .estimatedDaysToGermination(BOTANICAL_SPECIES_1_ESTIMATED_DAYS_TO_GERMINATION)
                .build();
        BotanicalSpeciesDTO botanicalSpecies = botanicalSpeciesService.createBotanicalSpecies(botanicalSpeciesDTO);

        SeedPackageDTO seedPackageDTO = SeedPackageDTO.builder()
                .name(SEED_PACKAGE_1_NAME)
                .botanicalSpeciesId(botanicalSpecies.id())
                .numberOfSeeds(SEED_PACKAGE_1_NUMBER_OF_SEEDS)
                .build();

        SeedPackageDTO seedPackage = seedPackageService.createSeedPackage(seedPackageDTO);

        SeedPackageDTO newSeedPackageDTO = SeedPackageDTO.builder()
                .name(SEED_PACKAGE_1_NAME_UPDATED)
                .botanicalSpeciesId(botanicalSpecies.id())
                .numberOfSeeds(SEED_PACKAGE_1_NUMBER_OF_SEEDS_UPDATED)
                .build();

        seedPackageService.updateSeedPackage(seedPackage.id(), newSeedPackageDTO);

        SeedPackage updatedSeedPackage = seedPackageService.getSeedPackageEntityByIdOrThrow(seedPackage.id());
        assertEquals(newSeedPackageDTO.name(), updatedSeedPackage.getName());
    }

    //todo add testCreateAndReadAndDelete
}
