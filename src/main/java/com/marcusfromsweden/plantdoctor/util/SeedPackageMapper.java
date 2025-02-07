package com.marcusfromsweden.plantdoctor.util;

import com.marcusfromsweden.plantdoctor.dto.SeedPackageDTO;
import com.marcusfromsweden.plantdoctor.entity.PlantSpecies;
import com.marcusfromsweden.plantdoctor.entity.SeedPackage;
import com.marcusfromsweden.plantdoctor.service.PlantSpeciesService;
import org.springframework.stereotype.Component;

@Component
public class SeedPackageMapper {

    private final PlantSpeciesService plantSpeciesService;

    public SeedPackageMapper(PlantSpeciesService plantSpeciesService) {
        this.plantSpeciesService = plantSpeciesService;
    }

    public SeedPackageDTO toDTO(SeedPackage seedPackage) {
        return new SeedPackageDTO(
                seedPackage.getId(),
                seedPackage.getPlantSpecies().getId(),
                seedPackage.getName(),
                seedPackage.getNumberOfSeeds()
        );
    }

    public SeedPackage toEntity(SeedPackageDTO seedPackageDTO) {
        PlantSpecies plantSpecies = plantSpeciesService.getPlantSpeciesEntityByIdOrThrow(seedPackageDTO.plantSpeciesId());

        SeedPackage seedPackage = new SeedPackage();
        seedPackage.setId(seedPackageDTO.id());
        seedPackage.setName(seedPackageDTO.name());
        seedPackage.setNumberOfSeeds(seedPackageDTO.numberOfSeeds());
        seedPackage.setPlantSpecies(plantSpecies);

        return seedPackage;
    }
}
