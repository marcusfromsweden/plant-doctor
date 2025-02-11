package com.marcusfromsweden.plantdoctor.util;

import com.marcusfromsweden.plantdoctor.dto.PlantCommentDTO;

public class PlantCommentTestHelper {
    public static PlantCommentDTO createDTO(Long id,
                                            Long plantId,
                                            String text) {
        return PlantCommentDTO.builder()
                .id(id)
                .plantId(plantId)
                .text(text)
                .build();
    }
}
