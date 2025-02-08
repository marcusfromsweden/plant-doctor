package com.marcusfromsweden.plantdoctor.util;

import com.marcusfromsweden.plantdoctor.dto.SeedPackageDTO;
import com.marcusfromsweden.plantdoctor.entity.BotanicalSpecies;
import com.marcusfromsweden.plantdoctor.entity.SeedPackage;
import com.marcusfromsweden.plantdoctor.service.BotanicalSpeciesService;
import org.springframework.stereotype.Component;

@Component
public class SeedPackageMapper {

    private final BotanicalSpeciesService botanicalSpeciesService;

    public SeedPackageMapper(BotanicalSpeciesService botanicalSpeciesService) {
        this.botanicalSpeciesService = botanicalSpeciesService;
    }

    public SeedPackageDTO toDTO(SeedPackage seedPackage) {
        return new SeedPackageDTO(
                seedPackage.getId(),
                seedPackage.getBotanicalSpecies().getId(),
                seedPackage.getName(),
                seedPackage.getNumberOfSeeds()
        );
    }

    public SeedPackage toEntity(SeedPackageDTO seedPackageDTO) {
        SeedPackage seedPackage = new SeedPackage();
        seedPackage.setId(seedPackageDTO.id());
        return toEntity(seedPackage, seedPackageDTO);
    }

    public SeedPackage toEntity(SeedPackage seedPackage,
                                SeedPackageDTO seedPackageDTO) {
        BotanicalSpecies botanicalSpecies =
                botanicalSpeciesService.getBotanicalSpeciesEntityByIdOrThrow(seedPackageDTO.botanicalSpeciesId());

        seedPackage.setName(seedPackageDTO.name());
        seedPackage.setNumberOfSeeds(seedPackageDTO.numberOfSeeds());
        seedPackage.setBotanicalSpecies(botanicalSpecies);

        return seedPackage;
    }
}
