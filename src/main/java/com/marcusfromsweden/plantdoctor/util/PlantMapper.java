package com.marcusfromsweden.plantdoctor.util;

import com.marcusfromsweden.plantdoctor.dto.PlantDTO;
import com.marcusfromsweden.plantdoctor.entity.GrowingLocation;
import com.marcusfromsweden.plantdoctor.entity.Plant;
import com.marcusfromsweden.plantdoctor.entity.PlantSpecies;
import com.marcusfromsweden.plantdoctor.repository.GrowingLocationRepository;
import com.marcusfromsweden.plantdoctor.repository.PlantSpeciesRepository;
import org.springframework.stereotype.Component;

@Component
public class PlantMapper {

    private final GrowingLocationRepository growingLocationRepository;
    private final PlantSpeciesRepository plantSpeciesRepository;

    public PlantMapper(GrowingLocationRepository growingLocationRepository,
                       PlantSpeciesRepository plantSpeciesRepository) {
        this.growingLocationRepository = growingLocationRepository;
        this.plantSpeciesRepository = plantSpeciesRepository;
    }

    public PlantDTO toDTO(Plant plant) {
        return new PlantDTO(
                plant.getId(),
                plant.getPlantSpecies().getId(),
                plant.getGrowingLocation().getId(),
                plant.getPlantingDate(),
                plant.getGerminationDate(),
                plant.getComment()
        );
    }

    public Plant toEntity(PlantDTO plantDTO) {
        Plant plant = new Plant();
        plant.setId(plantDTO.id());

        PlantSpecies plantSpecies = plantSpeciesRepository.findById(plantDTO.plantSpeciesId())
                .orElseThrow(() -> new RuntimeException("PlantSpecies not found with ID: " + plantDTO.plantSpeciesId()));
        plant.setPlantSpecies(plantSpecies);

        GrowingLocation growingLocation = growingLocationRepository.findById(plantDTO.growingLocationId())
                .orElseThrow(() -> new RuntimeException("GrowingLocation not found with ID: " + plantDTO.growingLocationId()));
        plant.setGrowingLocation(growingLocation);

        plant.setPlantingDate(plantDTO.plantingDate());
        plant.setGerminationDate(plantDTO.germinationDate());
        plant.setComment(plantDTO.comment());
        return plant;
    }
}