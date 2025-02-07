package com.marcusfromsweden.plantdoctor.dto;

import java.time.LocalDate;

public record QuickCreatePlantDTO(
        LocalDate plantingDate,
        String plantSpeciesName,
        String seedPackageName,
        String growingLocationName,
        String plantComment
) {
}