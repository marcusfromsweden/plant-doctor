package com.marcusfromsweden.plantdoctor.service;

import com.marcusfromsweden.plantdoctor.dto.PlantDTO;
import com.marcusfromsweden.plantdoctor.dto.QuickCreatePlantDTO;
import com.marcusfromsweden.plantdoctor.entity.GrowingLocation;
import com.marcusfromsweden.plantdoctor.entity.PlantComment;
import com.marcusfromsweden.plantdoctor.entity.SeedPackage;
import com.marcusfromsweden.plantdoctor.repository.PlantRepository;
import com.marcusfromsweden.plantdoctor.util.PostgresTestContainerTest;
import com.marcusfromsweden.plantdoctor.util.RepositoryTestHelper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PlantDoctorService_TestQuickCreateIT extends PostgresTestContainerTest {

    public static final String TEST_PLANT_SPECIES = "Test Species";
    public static final String TEST_PLANT_SEED_PACKAGE = "Test Seed Package";
    public static final String TEST_PLANT_LOCATION = "Test Location";
    public static final String THIS_PLANT_COMMENT = "This is a test comment";
    
    @Autowired
    private PlantDoctorService plantDoctorService;
    @Autowired
    private SeedPackageService seedPackageService;
    @Autowired
    private GrowingLocationService growingLocationService;
    @Autowired
    private PlantCommentService plantCommentService;

    @Autowired
    private PlantRepository plantRepository;

    @Autowired
    private RepositoryTestHelper repositoryTestHelper;

    @Test
    public void testQuickCreatePlant() {
        repositoryTestHelper.deleteAllData();

        QuickCreatePlantDTO quickCreatePlantDTO = new QuickCreatePlantDTO(
                LocalDate.now(),
                TEST_PLANT_SPECIES,
                TEST_PLANT_SEED_PACKAGE,
                TEST_PLANT_LOCATION,
                THIS_PLANT_COMMENT);

        PlantDTO createdPlant = plantDoctorService.quickCreatePlant(quickCreatePlantDTO);

        assertNotNull(createdPlant);
        assertNotNull(createdPlant.id());
        assertNotNull(plantRepository.findById(createdPlant.id()).orElse(null));
        assertEquals(quickCreatePlantDTO.plantingDate(), createdPlant.plantingDate());

        SeedPackage seedPackage =
                seedPackageService.getSeedPackageEntityByIdOrThrow(createdPlant.seedPackageId());
        assertEquals(quickCreatePlantDTO.seedPackageName(), seedPackage.getName());

        GrowingLocation growingLocation =
                growingLocationService.getGrowingLocationEntityByIdOrThrow(createdPlant.growingLocationId());
        assertEquals(quickCreatePlantDTO.growingLocationName(), growingLocation.getName());

        List<PlantComment> plantComment = plantCommentService.getCommentsByPlantId(createdPlant.id());
        assertEquals(1, plantComment.size());
        assertEquals(quickCreatePlantDTO.plantComment(), plantComment.get(0).getText());
    }
}