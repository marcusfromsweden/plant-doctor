package com.marcusfromsweden.plantdoctor.util;

import com.marcusfromsweden.plantdoctor.dto.SeedPackageDTO;

public class SeedPackageTestHelper {
    public static SeedPackageDTO createDTO(Long id,
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
