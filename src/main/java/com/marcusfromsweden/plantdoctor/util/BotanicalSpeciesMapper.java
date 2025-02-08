package com.marcusfromsweden.plantdoctor.util;

import com.marcusfromsweden.plantdoctor.dto.BotanicalSpeciesDTO;
import com.marcusfromsweden.plantdoctor.entity.BotanicalSpecies;
import org.springframework.stereotype.Component;

@Component
public class BotanicalSpeciesMapper {

    public BotanicalSpeciesDTO toDTO(BotanicalSpecies botanicalSpecies) {
        return new BotanicalSpeciesDTO(
                botanicalSpecies.getId(),
                botanicalSpecies.getName(),
                botanicalSpecies.getDescription(),
                botanicalSpecies.getEstimatedDaysToGermination()
        );
    }

    public BotanicalSpecies toEntity(BotanicalSpeciesDTO botanicalSpeciesDTO) {
        BotanicalSpecies botanicalSpecies = new BotanicalSpecies();
        botanicalSpecies.setId(botanicalSpeciesDTO.id());
        botanicalSpecies.setName(botanicalSpeciesDTO.name());
        botanicalSpecies.setDescription(botanicalSpeciesDTO.description());
        botanicalSpecies.setEstimatedDaysToGermination(botanicalSpeciesDTO.estimatedDaysToGermination());
        return botanicalSpecies;
    }
}