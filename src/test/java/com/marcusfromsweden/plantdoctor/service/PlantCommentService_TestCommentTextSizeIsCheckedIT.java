package com.marcusfromsweden.plantdoctor.service;

import com.marcusfromsweden.plantdoctor.dto.PlantDTO;
import com.marcusfromsweden.plantdoctor.dto.QuickCreatePlantDTO;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class PlantCommentService_TestCommentTextSizeIsCheckedIT {

    @Autowired
    private PlantCommentService plantCommentService;

    @Autowired
    private PlantDoctorService plantDoctorService;

    private PlantDTO plantToComment;

    @BeforeEach
    public void setUp() {
        QuickCreatePlantDTO plantDTO = new QuickCreatePlantDTO(
                LocalDate.now(),
                "Test Species",
                "Test Location", "");

        plantToComment = plantDoctorService.quickCreatePlant(plantDTO);
    }

    @Test
    public void testAddComment_TextTooShort_ThrowsConstraintViolationException() {
        assertThrows(ConstraintViolationException.class, () -> {
            plantCommentService.addComment(plantToComment.id(), "Te");
        });
    }

    @Test
    public void testAddComment_TextOkLength() {
        assertDoesNotThrow(() -> {
            plantCommentService.addComment(plantToComment.id(), "Tes");
        });
    }
}