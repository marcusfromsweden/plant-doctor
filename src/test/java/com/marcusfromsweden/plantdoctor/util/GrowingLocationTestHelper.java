package com.marcusfromsweden.plantdoctor.util;

import com.marcusfromsweden.plantdoctor.dto.GrowingLocationDTO;

public class GrowingLocationTestHelper {
    public static GrowingLocationDTO createDTO(Long id,
                                               String name) {
        return GrowingLocationDTO.builder()
                .id(id)
                .name(name)
                .build();
    }
}
