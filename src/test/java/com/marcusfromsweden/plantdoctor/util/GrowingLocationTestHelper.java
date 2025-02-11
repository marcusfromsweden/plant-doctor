package com.marcusfromsweden.plantdoctor.util;

import com.marcusfromsweden.plantdoctor.dto.GrowingLocationDTO;
import com.marcusfromsweden.plantdoctor.dto.mapper.GrowingLocationMapper;
import com.marcusfromsweden.plantdoctor.entity.GrowingLocation;
import org.springframework.stereotype.Component;

@Component
public class GrowingLocationTestHelper {
    private final GrowingLocationMapper growingLocationMapper;

    public GrowingLocationTestHelper(GrowingLocationMapper growingLocationMapper) {
        this.growingLocationMapper = growingLocationMapper;
    }

    public GrowingLocationDTO createDTO(Long id,
                                        String name) {
        return GrowingLocationDTO.builder()
                .id(id)
                .name(name)
                .build();
    }

    public GrowingLocation createEntity(Long id,
                                        String growingLocationName) {
        return growingLocationMapper.toEntity(createDTO(id, growingLocationName));
    }
}
