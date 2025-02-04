package com.marcusfromsweden.plantdoctor.dto;

import java.time.LocalDate;

public record PlantWebDTO(
        LocalDate plantingDate,
        String plantSpeciesName,
        String growingLocationName
) {

}