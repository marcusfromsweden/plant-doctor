package com.marcusfromsweden.plantdoctor.service;

import com.marcusfromsweden.plantdoctor.dto.BotanicalSpeciesDTO;
import com.marcusfromsweden.plantdoctor.dto.SeedPackageDTO;
import com.marcusfromsweden.plantdoctor.entity.SeedPackage;
import com.marcusfromsweden.plantdoctor.util.BotanicalSpeciesTestHelper;
import com.marcusfromsweden.plantdoctor.util.PostgresTestContainerTest;
import com.marcusfromsweden.plantdoctor.util.RepositoryTestHelper;
import com.marcusfromsweden.plantdoctor.util.SeedPackageTestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SeedPackageServiceCRUDIT extends PostgresTestContainerTest {

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

    @Test
    public void testCreateAndUpdate() {
        BotanicalSpeciesDTO botanicalSpeciesDTO = BotanicalSpeciesTestHelper.createDTO(
                null,
                BOTANICAL_SPECIES_1_LATIN_NAME,
                BOTANICAL_SPECIES_1_DESCRIPTION,
                BOTANICAL_SPECIES_1_ESTIMATED_DAYS_TO_GERMINATION
        );
        BotanicalSpeciesDTO botanicalSpecies =
                botanicalSpeciesService.createBotanicalSpecies(botanicalSpeciesDTO);

        SeedPackageDTO seedPackageDTO = SeedPackageTestHelper.createDTO(
                null,
                SEED_PACKAGE_1_NAME,
                botanicalSpecies.id(),
                SEED_PACKAGE_1_NUMBER_OF_SEEDS
        );

        SeedPackageDTO seedPackage = seedPackageService.createSeedPackage(seedPackageDTO);

        SeedPackageDTO updatedSeedPackageDTO = SeedPackageTestHelper.createDTO(null,
                                                                               SEED_PACKAGE_1_NAME_UPDATED,
                                                                               botanicalSpecies.id(),
                                                                               SEED_PACKAGE_1_NUMBER_OF_SEEDS_UPDATED
        );

        seedPackageService.updateSeedPackage(seedPackage.id(), updatedSeedPackageDTO);

        SeedPackage updatedSeedPackage = seedPackageService.getSeedPackageEntityByIdOrThrow(seedPackage.id());
        assertEquals(updatedSeedPackageDTO.name(), updatedSeedPackage.getName());
    }

    //FIXME add testCreateAndReadAndDelete
}
