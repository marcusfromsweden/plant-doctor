package com.marcusfromsweden.plantdoctor.service;

import com.marcusfromsweden.plantdoctor.dto.PlantDTO;
import com.marcusfromsweden.plantdoctor.dto.QuickCreatePlantDTO;
import com.marcusfromsweden.plantdoctor.repository.PlantRepository;
import com.marcusfromsweden.plantdoctor.util.PostgresTestContainerTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PlantDoctorService_TestQuickCreateIT extends PostgresTestContainerTest {

    @Autowired
    private PlantDoctorService plantDoctorService;

    @Autowired
    private PlantRepository plantRepository;

    @Test
    public void testQuickCreatePlant() {
        QuickCreatePlantDTO quickCreatePlantDTO = new QuickCreatePlantDTO(
                LocalDate.now(),
                "Test Species",
                "Test Location",
                "This is a test comment");

        PlantDTO createdPlant = plantDoctorService.quickCreatePlant(quickCreatePlantDTO);

        assertNotNull(createdPlant);
        assertNotNull(createdPlant.id());
        assertNotNull(plantRepository.findById(createdPlant.id()).orElse(null));
    }
}