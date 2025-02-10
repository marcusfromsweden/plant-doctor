package com.marcusfromsweden.plantdoctor.service;

import com.marcusfromsweden.plantdoctor.dto.BotanicalSpeciesDTO;
import com.marcusfromsweden.plantdoctor.dto.GrowingLocationDTO;
import com.marcusfromsweden.plantdoctor.dto.PlantDTO;
import com.marcusfromsweden.plantdoctor.dto.SeedPackageDTO;
import com.marcusfromsweden.plantdoctor.util.PostgresTestContainerTest;
import com.marcusfromsweden.plantdoctor.util.RepositoryTestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PlantService_VerifyCRUDIT extends PostgresTestContainerTest {

    public static final LocalDate PLANT_1_PLANTING_DATE = LocalDate.of(2025, 1, 1);
    public static final LocalDate PLANT_1_GERMINATION_DATE = LocalDate.of(2025, 1, 15);

    public static final LocalDate PLANT_2_PLANTING_DATE = LocalDate.of(2025, 2, 1);
    public static final LocalDate PLANT_2_GERMINATION_DATE = LocalDate.of(2025, 2, 15);
    public static final LocalDate PLANT_2_PLANTING_DATE_UPDATED = LocalDate.of(2025, 2, 2);
    public static final LocalDate PLANT_2_GERMINATION_DATE_UPDATED = LocalDate.of(2025, 2, 16);

    public static final String BOTANICAL_SPECIES_LATIN_NAME = "Botanical Species 1";
    public static final String BOTANICAL_SPECIES_DESCRIPTION = "Some description";
    public static final int BOTANICAL_SPECIES_ESTIMATED_DAYS_TO_GERMINATION = 7;

    public static final String SEED_PACK_NAME = "Seed Pack 1";
    public static final int SEED_PACK_NUMBER_OF_SEEDS = 100;

    @Autowired
    private PlantService plantService;
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

        BotanicalSpeciesDTO botanicalSpeciesDetails = BotanicalSpeciesDTO.builder()
                .latinName(BOTANICAL_SPECIES_LATIN_NAME)
                .description(BOTANICAL_SPECIES_DESCRIPTION)
                .estimatedDaysToGermination(BOTANICAL_SPECIES_ESTIMATED_DAYS_TO_GERMINATION)
                .build();
        BotanicalSpeciesDTO botanicalSpeciesDTO = botanicalSpeciesService.createBotanicalSpecies(botanicalSpeciesDetails);

        SeedPackageDTO seedPackageDetails = SeedPackageDTO.builder()
                .name(SEED_PACK_NAME)
                .botanicalSpeciesId(botanicalSpeciesDTO.id())
                .numberOfSeeds(SEED_PACK_NUMBER_OF_SEEDS)
                .build();
        seedPackage = seedPackageService.createSeedPackage(seedPackageDetails);

        GrowingLocationDTO growingLocationDetails = GrowingLocationDTO.builder()
                .name("Growing Location 1")
                .build();
        growingLocation = growingLocationService.createGrowingLocation(growingLocationDetails);
    }

    @Test
    public void testCreateAndReadAndDelete() {
        PlantDTO plantDetails = PlantDTO.builder()
                .seedPackageId(seedPackage.id())
                .growingLocationId(growingLocation.id())
                .plantingDate(PLANT_1_PLANTING_DATE)
                .germinationDate(PLANT_1_GERMINATION_DATE)
                .build();

        assertEquals(0, plantService.getAllPlants().size());

        PlantDTO plant = plantService.createPlant(plantDetails);

        assertEquals(1, plantService.getAllPlants().size());
        assertNotNull(plant);
        assertNotNull(plant.id());
        assertNotNull(plantService.getPlantById(plant.id()));
        assertEquals(plantDetails.plantingDate(), plant.plantingDate());
        assertEquals(plantDetails.germinationDate(), plant.germinationDate());
        assertEquals(plantDetails.seedPackageId(), plant.seedPackageId());
        assertEquals(plantDetails.growingLocationId(), plant.growingLocationId());

        plantService.deletePlant(plant.id());
        assertEquals(0, plantService.getAllPlants().size());
    }

    @Test
    public void testCreateAndUpdate() {
        PlantDTO plantDetails = PlantDTO.builder()
                .seedPackageId(seedPackage.id())
                .growingLocationId(growingLocation.id())
                .plantingDate(PLANT_2_PLANTING_DATE)
                .germinationDate(PLANT_2_GERMINATION_DATE)
                .build();

        //FIXME add checks for modified seed package and growing location
        PlantDTO plant = plantService.createPlant(plantDetails);

        PlantDTO updatedPlantDetails = PlantDTO.builder()
                .seedPackageId(seedPackage.id())
                .growingLocationId(growingLocation.id())
                .plantingDate(PLANT_2_PLANTING_DATE_UPDATED)
                .germinationDate(PLANT_2_GERMINATION_DATE_UPDATED)
                .build();
        PlantDTO updatedPlant = plantService.updatePlant(plant.id(), updatedPlantDetails);

        assertEquals(updatedPlantDetails.plantingDate(), updatedPlant.plantingDate());
        assertEquals(updatedPlantDetails.germinationDate(), updatedPlant.germinationDate());
    }
}