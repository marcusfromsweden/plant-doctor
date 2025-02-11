package com.marcusfromsweden.plantdoctor.util;

import com.marcusfromsweden.plantdoctor.dto.PlantDTO;

import java.time.LocalDate;

public class PlantTestHelper {
    public static PlantDTO createDTO(Long id,
                                     Long seedPackageId,
                                     Long growingLocationId,
                                     LocalDate plantingDate,
                                     LocalDate germinationDate) {
        return PlantDTO.builder()
                .id(id)
                .seedPackageId(seedPackageId)
                .growingLocationId(growingLocationId)
                .plantingDate(plantingDate)
                .germinationDate(germinationDate)
                .build();
    }
}