package com.marcusfromsweden.plantdoctor.util;

import com.marcusfromsweden.plantdoctor.dto.PlantSpeciesDTO;
import com.marcusfromsweden.plantdoctor.entity.PlantSpecies;

public class PlantSpeciesMapper {

    public static PlantSpeciesDTO toDTO(PlantSpecies plantSpecies) {
        return new PlantSpeciesDTO(
                plantSpecies.getId(),
                plantSpecies.getName(),
                plantSpecies.getDescription(),
                plantSpecies.getEstimatedDaysToGermination()
        );
    }

    public static PlantSpecies toEntity(PlantSpeciesDTO plantSpeciesDTO) {
        PlantSpecies plantSpecies = new PlantSpecies();
        plantSpecies.setId(plantSpeciesDTO.id());
        plantSpecies.setName(plantSpeciesDTO.name());
        plantSpecies.setDescription(plantSpeciesDTO.description());
        plantSpecies.setEstimatedDaysToGermination(plantSpeciesDTO.estimatedDaysToGermination());
        return plantSpecies;
    }
}