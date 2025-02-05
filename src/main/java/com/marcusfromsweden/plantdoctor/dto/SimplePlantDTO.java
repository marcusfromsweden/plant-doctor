package com.marcusfromsweden.plantdoctor.dto;

import java.time.LocalDate;

public record SimplePlantDTO(
        LocalDate plantingDate,
        String plantSpeciesName,
        String growingLocationName,
        String plantComment
) {

}