package com.marcusfromsweden.plantdoctor.util;

import com.marcusfromsweden.plantdoctor.dto.GrowingLocationDTO;
import org.springframework.stereotype.Component;

@Component
public class GrowingLocationTestHelper {
    public GrowingLocationDTO createDTO(Long id,
                                        String name) {
        return GrowingLocationDTO.builder()
                .id(id)
                .name(name)
                .build();
    }
}
