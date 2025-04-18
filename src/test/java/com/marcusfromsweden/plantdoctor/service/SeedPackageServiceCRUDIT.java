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
import org.springframework.context.annotation.Import;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import({SeedPackageTestHelper.class, BotanicalSpeciesTestHelper.class})
public class SeedPackageServiceCRUDIT extends PostgresTestContainerTest {

    private static final String BOTANICAL_SPECIES_1_LATIN_NAME = "Botanical Species 1";
    private static final String BOTANICAL_SPECIES_1_DESCRIPTION = "Some description";
    private static final int BOTANICAL_SPECIES_1_ESTIMATED_DAYS_TO_GERMINATION = 7;
    private static final String SEED_PACKAGE_1_NAME = "Some seed pack 1";
    private static final int SEED_PACKAGE_1_NUMBER_OF_SEEDS = 100;
    private static final String SEED_PACKAGE_1_NAME_UPDATED = "SP 2";
    private static final int SEED_PACKAGE_1_NUMBER_OF_SEEDS_UPDATED = 200;

    @Autowired
    private SeedPackageService seedPackageService;
    @Autowired
    private BotanicalSpeciesService botanicalSpeciesService;
    @Autowired
    private SeedPackageTestHelper seedPackageTestHelper;
    @Autowired
    private BotanicalSpeciesTestHelper botanicalSpeciesTestHelper;
    @Autowired
    private RepositoryTestHelper repositoryTestHelper;

    @BeforeEach
    public void setupTest() {
        repositoryTestHelper.deleteAllData();
    }

    @Test
    public void shouldCreateAndUpdate() {
        BotanicalSpeciesDTO botanicalSpeciesDTO = botanicalSpeciesTestHelper.createDTO(
                null,
                BOTANICAL_SPECIES_1_LATIN_NAME,
                BOTANICAL_SPECIES_1_DESCRIPTION,
                BOTANICAL_SPECIES_1_ESTIMATED_DAYS_TO_GERMINATION
        );
        BotanicalSpeciesDTO botanicalSpecies =
                botanicalSpeciesService.createBotanicalSpecies(botanicalSpeciesDTO);

        SeedPackageDTO seedPackageDTO = seedPackageTestHelper.createDTO(
                null,
                SEED_PACKAGE_1_NAME,
                botanicalSpecies.id(),
                SEED_PACKAGE_1_NUMBER_OF_SEEDS
        );

        SeedPackageDTO seedPackage = seedPackageService.createSeedPackage(seedPackageDTO);

        SeedPackageDTO updatedSeedPackageDTO = seedPackageTestHelper.createDTO(null,
                                                                               SEED_PACKAGE_1_NAME_UPDATED,
                                                                               botanicalSpecies.id(),
                                                                               SEED_PACKAGE_1_NUMBER_OF_SEEDS_UPDATED
        );

        seedPackageService.updateSeedPackage(seedPackage.id(), updatedSeedPackageDTO);

        SeedPackage updatedSeedPackage = seedPackageService.getSeedPackageEntityByIdOrThrow(seedPackage.id());
        assertEquals(updatedSeedPackageDTO.name(), updatedSeedPackage.getName());
    }

    //FIXME add shouldCreateAndDelete
}
