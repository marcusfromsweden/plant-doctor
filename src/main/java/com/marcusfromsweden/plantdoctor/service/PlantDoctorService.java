package com.marcusfromsweden.plantdoctor.service;

import com.marcusfromsweden.plantdoctor.dto.BotanicalSpeciesDTO;
import com.marcusfromsweden.plantdoctor.dto.GrowingLocationDTO;
import com.marcusfromsweden.plantdoctor.dto.PlantCommentDTO;
import com.marcusfromsweden.plantdoctor.dto.PlantDTO;
import com.marcusfromsweden.plantdoctor.dto.QuickCreatePlantDTO;
import com.marcusfromsweden.plantdoctor.dto.SeedPackageDTO;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class PlantDoctorService {

    private final Logger log = LoggerFactory.getLogger(PlantDoctorService.class);

    private final PlantService plantService;
    private final BotanicalSpeciesService botanicalSpeciesService;
    private final GrowingLocationService growingLocationService;
    private final PlantCommentService plantCommentService;
    private final SeedPackageService seedPackageService;

    public PlantDoctorService(PlantService plantService,
                              BotanicalSpeciesService botanicalSpeciesService,
                              GrowingLocationService growingLocationService,
                              PlantCommentService plantCommentService,
                              SeedPackageService seedPackageService) {
        this.plantService = plantService;
        this.botanicalSpeciesService = botanicalSpeciesService;
        this.growingLocationService = growingLocationService;
        this.plantCommentService = plantCommentService;
        this.seedPackageService = seedPackageService;
    }

    @Transactional
    public PlantDTO quickCreatePlant(QuickCreatePlantDTO quickCreatePlantDTO) {
        log.debug("Quick creating a plant with data: {}", quickCreatePlantDTO);
        BotanicalSpeciesDTO botanicalSpecies =
                botanicalSpeciesService.getOrCreateBotanicalSpeciesByName(quickCreatePlantDTO.botanicalSpeciesName());
        SeedPackageDTO seedPackage =
                seedPackageService.getOrCreateSeedPackageByNameAndBotanicalSpeciesId(quickCreatePlantDTO.seedPackageName(), botanicalSpecies.id());
        GrowingLocationDTO growingLocation =
                growingLocationService.getOrCreateGrowingLocationByName(quickCreatePlantDTO.growingLocationName());

        log.debug("Creating a new plant with species {}, seed package {} and location {}",
                  botanicalSpecies.latinName(), seedPackage.name(), growingLocation.name());
        PlantDTO plant = plantService.createPlant(PlantDTO.builder()
                                                          .plantingDate(quickCreatePlantDTO.plantingDate())
                                                          .seedPackageId(seedPackage.id())
                                                          .growingLocationId(growingLocation.id())
                                                          .build());

        if (StringUtils.hasText(quickCreatePlantDTO.plantComment())) {
            PlantCommentDTO plantComment = plantCommentService.createComment(plant.id(), quickCreatePlantDTO.plantComment());
            log.debug("Added a comment to the plant: {}", plantComment);
        }

        return plant;
    }
}
