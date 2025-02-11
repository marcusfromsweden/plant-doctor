package com.marcusfromsweden.plantdoctor.util;

import com.marcusfromsweden.plantdoctor.dto.PlantCommentDTO;
import org.springframework.stereotype.Component;

@Component
public class PlantCommentTestHelper {
    public PlantCommentDTO createDTO(Long id,
                                     Long plantId,
                                     String text) {
        return PlantCommentDTO.builder()
                .id(id)
                .plantId(plantId)
                .text(text)
                .build();
    }
}
