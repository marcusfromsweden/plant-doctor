package com.marcusfromsweden.plantdoctor.util;

import com.marcusfromsweden.plantdoctor.dto.PlantDTO;
import com.marcusfromsweden.plantdoctor.entity.GrowingLocation;
import com.marcusfromsweden.plantdoctor.entity.Plant;
import com.marcusfromsweden.plantdoctor.entity.SeedPackage;
import com.marcusfromsweden.plantdoctor.service.GrowingLocationService;
import com.marcusfromsweden.plantdoctor.service.SeedPackageService;
import org.springframework.stereotype.Component;

@Component
public class PlantMapper {

    private final GrowingLocationService growingLocationService;
    private final SeedPackageService seedPackageService;

    public PlantMapper(GrowingLocationService growingLocationService,
                       SeedPackageService seedPackageService) {
        this.growingLocationService = growingLocationService;
        this.seedPackageService = seedPackageService;
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

    public Plant toEntity(PlantDTO plantDTO) {
        Plant plant = new Plant();
        plant.setId(plantDTO.id());
        return toEntity(plant, plantDTO);
    }

    //todo consider using this from PlantService
    public Plant toEntity(Plant plant,
                          PlantDTO plantDTO) {
        SeedPackage seedPackage = seedPackageService.getSeedPackageEntityByIdOrThrow(plantDTO.seedPackageId());
        plant.setSeedPackage(seedPackage);

        GrowingLocation growingLocation = growingLocationService.getGrowingLocationEntityByIdOrThrow(plantDTO.growingLocationId());
        plant.setGrowingLocation(growingLocation);

        plant.setPlantingDate(plantDTO.plantingDate());
        plant.setGerminationDate(plantDTO.germinationDate());
        return plant;
    }
}