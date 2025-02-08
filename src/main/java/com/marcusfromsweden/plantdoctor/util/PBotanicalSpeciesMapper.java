package com.marcusfromsweden.plantdoctor.util;

import com.marcusfromsweden.plantdoctor.dto.BotanicalSpeciesDTO;
import com.marcusfromsweden.plantdoctor.entity.BotanicalSpecies;

//todo change to @Component and make static methods non-static
public class PBotanicalSpeciesMapper {

    public static BotanicalSpeciesDTO toDTO(BotanicalSpecies botanicalSpecies) {
        return new BotanicalSpeciesDTO(
                botanicalSpecies.getId(),
                botanicalSpecies.getName(),
                botanicalSpecies.getDescription(),
                botanicalSpecies.getEstimatedDaysToGermination()
        );
    }

    public static BotanicalSpecies toEntity(BotanicalSpeciesDTO botanicalSpeciesDTO) {
        BotanicalSpecies botanicalSpecies = new BotanicalSpecies();
        botanicalSpecies.setId(botanicalSpeciesDTO.id());
        botanicalSpecies.setName(botanicalSpeciesDTO.name());
        botanicalSpecies.setDescription(botanicalSpeciesDTO.description());
        botanicalSpecies.setEstimatedDaysToGermination(botanicalSpeciesDTO.estimatedDaysToGermination());
        return botanicalSpecies;
    }
}