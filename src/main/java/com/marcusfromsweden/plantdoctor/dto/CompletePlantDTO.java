package com.marcusfromsweden.plantdoctor.dto;

import java.time.LocalDate;

public record CompletePlantDTO(
        LocalDate plantingDate,
        String plantSpeciesName,
        String growingLocationName
) {

}