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
public class PlantCommentService_TestCommentTextSizeConstraintIT extends PostgresTestContainerTest {

    public static final String PLANT_BOTANICAL_SPECIES = "Test Species";
    public static final String PLANT_SEED_PACKAGE = "Test Seed Package";
    public static final String PLANT_GROWING_LOCATION = "Test Location";
    public static final String PLANT_COMMENT = "";
    public static final String PLANT_COMMENT_1_TOO_SHORT = "Te";
    public static final String PLANT_COMMENT_2_LONG_ENOUGH = "Tes";
    @Autowired
    private PlantCommentService plantCommentService;
    @Autowired
    private PlantDoctorService plantDoctorService;

    private PlantDTO plantToComment;

    @BeforeEach
    public void setUp() {
        QuickCreatePlantDTO plantDTO = new QuickCreatePlantDTO(
                LocalDate.now(),
                PLANT_BOTANICAL_SPECIES,
                PLANT_SEED_PACKAGE,
                PLANT_GROWING_LOCATION,
                PLANT_COMMENT);

        plantToComment = plantDoctorService.quickCreatePlant(plantDTO);
    }

    @Test
    public void testCreateComment_TextTooShort_ThrowsConstraintViolationException() {
        assertThrows(ConstraintViolationException.class, () ->
                plantCommentService.createComment(plantToComment.id(), PLANT_COMMENT_1_TOO_SHORT)
        );
    }

    @Test
    public void testCreateComment_TextOkLength() {
        assertDoesNotThrow(() -> {
            plantCommentService.createComment(plantToComment.id(), PLANT_COMMENT_2_LONG_ENOUGH);
        });
    }

}