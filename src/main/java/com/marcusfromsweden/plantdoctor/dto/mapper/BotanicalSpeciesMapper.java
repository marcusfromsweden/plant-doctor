package com.marcusfromsweden.plantdoctor.dto.mapper;

import com.marcusfromsweden.plantdoctor.dto.BotanicalSpeciesDTO;
import com.marcusfromsweden.plantdoctor.entity.BotanicalSpecies;
import org.springframework.stereotype.Component;

@Component
public class BotanicalSpeciesMapper {

    public BotanicalSpeciesDTO toDTO(BotanicalSpecies botanicalSpecies) {
        return new BotanicalSpeciesDTO(
                botanicalSpecies.getId(),
                botanicalSpecies.getLatinName(),
                botanicalSpecies.getDescription(),
                botanicalSpecies.getEstimatedDaysToGermination()
        );
    }

    public void updateEntityUsingDTO(BotanicalSpecies botanicalSpecies,
                                     BotanicalSpeciesDTO botanicalSpeciesDTO) {
        toEntity(botanicalSpecies, botanicalSpeciesDTO);
    }

    public BotanicalSpecies toEntity(BotanicalSpeciesDTO botanicalSpeciesDetails) {
        BotanicalSpecies botanicalSpecies = new BotanicalSpecies();
        return toEntity(botanicalSpecies, botanicalSpeciesDetails);
    }

    private BotanicalSpecies toEntity(BotanicalSpecies botanicalSpecies,
                                      BotanicalSpeciesDTO botanicalSpeciesDetails) {
        botanicalSpecies.setLatinName(botanicalSpeciesDetails.latinName());
        botanicalSpecies.setDescription(botanicalSpeciesDetails.description());
        botanicalSpecies.setEstimatedDaysToGermination(botanicalSpeciesDetails.estimatedDaysToGermination());
        return botanicalSpecies;
    }
}