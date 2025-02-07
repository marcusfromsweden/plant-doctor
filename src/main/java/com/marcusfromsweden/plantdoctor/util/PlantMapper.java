package com.marcusfromsweden.plantdoctor.util;

import com.marcusfromsweden.plantdoctor.dto.PlantDTO;
import com.marcusfromsweden.plantdoctor.entity.GrowingLocation;
import com.marcusfromsweden.plantdoctor.entity.Plant;
import com.marcusfromsweden.plantdoctor.entity.SeedPackage;
import com.marcusfromsweden.plantdoctor.repository.GrowingLocationRepository;
import com.marcusfromsweden.plantdoctor.repository.SeedPackageRepository;
import org.springframework.stereotype.Component;

@Component
public class PlantMapper {

    //TODO change from using repositories to services
    private final GrowingLocationRepository growingLocationRepository;
    private final SeedPackageRepository seedPackageRepository;

    public PlantMapper(GrowingLocationRepository growingLocationRepository,
                       SeedPackageRepository seedPackageRepository) {
        this.growingLocationRepository = growingLocationRepository;
        this.seedPackageRepository = seedPackageRepository;
    }

    public PlantDTO toDTO(Plant plant) {
        return new PlantDTO(
                plant.getId(),
                plant.getSeedPackage().getId(),
                plant.getGrowingLocation().getId(),
                plant.getPlantingDate(),
                plant.getGerminationDate()
        );
    }

    //todo consider using this from PlantService
    public Plant toEntity(PlantDTO plantDTO) {
        Plant plant = new Plant();
        plant.setId(plantDTO.id());

        //todo use entity related exceptions
        SeedPackage seedPackage = seedPackageRepository.findById(plantDTO.seedPackageId())
                .orElseThrow(() -> new RuntimeException("SeedPackage not found with ID: " + plantDTO.seedPackageId()));
        plant.setSeedPackage(seedPackage);

        GrowingLocation growingLocation = growingLocationRepository.findById(plantDTO.growingLocationId())
                .orElseThrow(() -> new RuntimeException("GrowingLocation not found with ID: " + plantDTO.growingLocationId()));
        plant.setGrowingLocation(growingLocation);

        plant.setPlantingDate(plantDTO.plantingDate());
        plant.setGerminationDate(plantDTO.germinationDate());
        return plant;
    }
}