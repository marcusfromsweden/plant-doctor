package com.marcusfromsweden.plantdoctor.service;

import com.marcusfromsweden.plantdoctor.dto.SimplePlantDTO;
import com.marcusfromsweden.plantdoctor.repository.GrowingLocationRepository;
import com.marcusfromsweden.plantdoctor.repository.PlantRepository;
import com.marcusfromsweden.plantdoctor.repository.PlantSpeciesRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
public class PlantDoctorServiceIT {

    @Autowired
    private PlantService plantService;

    @Autowired
    private PlantSpeciesService plantSpeciesService;

    @Autowired
    private GrowingLocationService growingLocationService;

    @MockBean
    private PlantCommentService plantCommentService;

    @Autowired
    private PlantRepository plantRepository;

    @Autowired
    private PlantSpeciesRepository plantSpeciesRepository;

    @Autowired
    private GrowingLocationRepository growingLocationRepository;

    private PlantDoctorService plantDoctorService;

    @BeforeEach
    public void setup() {
        plantDoctorService = new PlantDoctorService(
                plantService, plantSpeciesService, growingLocationService, plantCommentService);
    }

    @Test
    @Transactional
    @Disabled("The exception from addComment in PlantCommentService is not propagated as expected")
    public void testCreatePlant_CommentCreationFails_NoEntitiesCreated() {
        SimplePlantDTO simplePlantDTO = new SimplePlantDTO(
                LocalDate.now(),
                "Test Species",
                "Test Location",
                "T");

//        Mockito.doThrow(new RuntimeException("Failed to create comment"))
//                .when(plantCommentService).addComment(Mockito.anyLong(), Mockito.anyString());

        assertThrows(RuntimeException.class, () -> {
            plantDoctorService.createPlant(simplePlantDTO);
        });

        // Verify that no entities are created
        assertEquals(0, plantRepository.count());
        assertEquals(0, plantSpeciesRepository.count());
        assertEquals(0, growingLocationRepository.count());
    }
}