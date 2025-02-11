package com.marcusfromsweden.plantdoctor.service;

import com.marcusfromsweden.plantdoctor.dto.BotanicalSpeciesDTO;
import com.marcusfromsweden.plantdoctor.dto.GrowingLocationDTO;
import com.marcusfromsweden.plantdoctor.dto.PlantDTO;
import com.marcusfromsweden.plantdoctor.dto.SeedPackageDTO;
import com.marcusfromsweden.plantdoctor.util.BotanicalSpeciesTestHelper;
import com.marcusfromsweden.plantdoctor.util.GrowingLocationTestHelper;
import com.marcusfromsweden.plantdoctor.util.PlantTestHelper;
import com.marcusfromsweden.plantdoctor.util.PostgresTestContainerTest;
import com.marcusfromsweden.plantdoctor.util.RepositoryTestHelper;
import com.marcusfromsweden.plantdoctor.util.SeedPackageTestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import({PlantTestHelper.class, BotanicalSpeciesTestHelper.class, SeedPackageTestHelper.class, GrowingLocationTestHelper.class})
public class PlantServiceCRUDIT extends PostgresTestContainerTest {

    public static final LocalDate PLANT_1_PLANTING_DATE = LocalDate.of(2025, 1, 1);
    public static final LocalDate PLANT_1_GERMINATION_DATE = LocalDate.of(2025, 1, 15);

    public static final LocalDate PLANT_2_PLANTING_DATE = LocalDate.of(2025, 2, 1);
    public static final LocalDate PLANT_2_GERMINATION_DATE = LocalDate.of(2025, 2, 15);
    public static final LocalDate PLANT_2_PLANTING_DATE_UPDATED = LocalDate.of(2025, 2, 2);
    public static final LocalDate PLANT_2_GERMINATION_DATE_UPDATED = LocalDate.of(2025, 2, 16);

    public static final String GROWING_LOCATION_NAME = "Growing Location";

    public static final String BOTANICAL_SPECIES_LATIN_NAME = "Botanical Species";
    public static final String BOTANICAL_SPECIES_DESCRIPTION = "Some description";
    public static final int BOTANICAL_SPECIES_ESTIMATED_DAYS_TO_GERMINATION = 7;

    public static final String SEED_PACK_NAME = "Seed Pack 1";
    public static final int SEED_PACK_NUMBER_OF_SEEDS = 100;

    @Autowired
    private PlantService plantService;
    @Autowired
    private PlantTestHelper plantTestHelper;
    @Autowired
    private BotanicalSpeciesTestHelper botanicalSpeciesTestHelper;
    @Autowired
    private SeedPackageTestHelper seedPackageTestHelper;
    @Autowired
    private GrowingLocationTestHelper growingLocationTestHelper;
    @Autowired
    private RepositoryTestHelper repositoryTestHelper;
    @Autowired
    private BotanicalSpeciesService botanicalSpeciesService;
    @Autowired
    private SeedPackageService seedPackageService;
    @Autowired
    private GrowingLocationService growingLocationService;

    private SeedPackageDTO seedPackage;
    private GrowingLocationDTO growingLocation;

    @BeforeEach
    public void setUp() {
        repositoryTestHelper.deleteAllData();

        BotanicalSpeciesDTO botanicalSpeciesDetails = botanicalSpeciesTestHelper.createDTO(null,
                                                                                           BOTANICAL_SPECIES_LATIN_NAME,
                                                                                           BOTANICAL_SPECIES_DESCRIPTION,
                                                                                           BOTANICAL_SPECIES_ESTIMATED_DAYS_TO_GERMINATION);
        BotanicalSpeciesDTO botanicalSpeciesDTO = botanicalSpeciesService.createBotanicalSpecies(botanicalSpeciesDetails);

        SeedPackageDTO seedPackageDTO = seedPackageTestHelper.createDTO(null,
                                                                        SEED_PACK_NAME,
                                                                        botanicalSpeciesDTO.id(),
                                                                        SEED_PACK_NUMBER_OF_SEEDS);
        seedPackage = seedPackageService.createSeedPackage(seedPackageDTO);

        GrowingLocationDTO growingLocationDTO = growingLocationTestHelper.createDTO(null, GROWING_LOCATION_NAME);
        growingLocation = growingLocationService.createGrowingLocation(growingLocationDTO);
    }

    @Test
    public void shouldCreateAndDelete() {
        PlantDTO plantDTO = plantTestHelper.createDTO(null,
                                                      seedPackage.id(),
                                                      growingLocation.id(),
                                                      PLANT_1_PLANTING_DATE,
                                                      PLANT_1_GERMINATION_DATE);

        assertEquals(0, plantService.getAllPlants().size());

        PlantDTO plant = plantService.createPlant(plantDTO);

        assertEquals(1, plantService.getAllPlants().size());
        assertNotNull(plant);
        assertNotNull(plant.id());
        assertTrue(plantService.getPlantById(plant.id()).isPresent());
        assertEquals(plantDTO.plantingDate(), plant.plantingDate());
        assertEquals(plantDTO.germinationDate(), plant.germinationDate());
        assertEquals(plantDTO.seedPackageId(), plant.seedPackageId());
        assertEquals(plantDTO.growingLocationId(), plant.growingLocationId());

        plantService.deletePlant(plant.id());
        assertEquals(0, plantService.getAllPlants().size());
    }

    @Test
    public void shouldCreateAndUpdate() {
        PlantDTO plantDTO = plantTestHelper.createDTO(null,
                                                      seedPackage.id(),
                                                      growingLocation.id(),
                                                      PLANT_2_PLANTING_DATE,
                                                      PLANT_2_GERMINATION_DATE);

        //FIXME add checks for modified seed package and growing location
        PlantDTO plant = plantService.createPlant(plantDTO);

        PlantDTO updatedPlantDTO = plantTestHelper.createDTO(plant.id(),
                                                             seedPackage.id(),
                                                             growingLocation.id(),
                                                             PLANT_2_PLANTING_DATE_UPDATED,
                                                             PLANT_2_GERMINATION_DATE_UPDATED);
        PlantDTO updatedPlant = plantService.updatePlant(plant.id(), updatedPlantDTO);

        assertEquals(updatedPlantDTO.plantingDate(), updatedPlant.plantingDate());
        assertEquals(updatedPlantDTO.germinationDate(), updatedPlant.germinationDate());
    }
}