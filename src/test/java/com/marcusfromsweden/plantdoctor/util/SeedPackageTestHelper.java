package com.marcusfromsweden.plantdoctor.util;

import com.marcusfromsweden.plantdoctor.dto.SeedPackageDTO;
import org.springframework.stereotype.Component;

@Component
public class SeedPackageTestHelper {
    public SeedPackageDTO createDTO(Long id,
                                    String name,
                                    Long botanicalSpeciesId,
                                    int numberOfSeeds) {
        return SeedPackageDTO.builder()
                .id(id)
                .name(name)
                .botanicalSpeciesId(botanicalSpeciesId)
                .numberOfSeeds(numberOfSeeds)
                .build();
    }
}
