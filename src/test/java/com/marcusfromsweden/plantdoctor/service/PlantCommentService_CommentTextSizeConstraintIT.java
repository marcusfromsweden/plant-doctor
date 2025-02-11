package com.marcusfromsweden.plantdoctor.service;

import com.marcusfromsweden.plantdoctor.dto.PlantDTO;
import com.marcusfromsweden.plantdoctor.dto.QuickCreatePlantDTO;
import com.marcusfromsweden.plantdoctor.util.PostgresTestContainerTest;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PlantCommentService_CommentTextSizeConstraintIT extends PostgresTestContainerTest {

    private static final String PLANT_BOTANICAL_SPECIES = "Test Species";
    private static final String PLANT_SEED_PACKAGE = "Test Seed Package";
    private static final String PLANT_GROWING_LOCATION = "Test Location";
    private static final String PLANT_COMMENT = "";
    private static final String PLANT_COMMENT_1_TOO_SHORT = "Te";
    private static final String PLANT_COMMENT_2_LONG_ENOUGH = "Tes";

    @Autowired
    private PlantCommentService plantCommentService;
    @Autowired
    private PlantDoctorService plantDoctorService;

    private PlantDTO plantToComment;

    @BeforeEach
    public void setupTest() {
        QuickCreatePlantDTO plantDTO = new QuickCreatePlantDTO(
                LocalDate.now(),
                PLANT_BOTANICAL_SPECIES,
                PLANT_SEED_PACKAGE,
                PLANT_GROWING_LOCATION,
                PLANT_COMMENT);

        plantToComment = plantDoctorService.quickCreatePlant(plantDTO);
    }

    @Test
    public void shouldNotAllowCommentWithTooShortText() {
        assertThrows(ConstraintViolationException.class, () ->
                plantCommentService.createComment(plantToComment.id(), PLANT_COMMENT_1_TOO_SHORT)
        );
    }

    @Test
    public void shouldAllowCommentWithLongEnoughText() {
        assertDoesNotThrow(() -> {
            plantCommentService.createComment(plantToComment.id(), PLANT_COMMENT_2_LONG_ENOUGH);
        });
    }

}