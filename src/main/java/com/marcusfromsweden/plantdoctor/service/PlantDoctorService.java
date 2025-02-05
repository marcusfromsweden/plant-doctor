package com.marcusfromsweden.plantdoctor.service;

import com.marcusfromsweden.plantdoctor.dto.GrowingLocationDTO;
import com.marcusfromsweden.plantdoctor.dto.PlantDTO;
import com.marcusfromsweden.plantdoctor.dto.PlantSpeciesDTO;
import com.marcusfromsweden.plantdoctor.dto.SimplePlantDTO;
import com.marcusfromsweden.plantdoctor.entity.PlantComment;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class PlantDoctorService {

    private final Logger log = LoggerFactory.getLogger(PlantDoctorService.class);

    private final PlantService plantService;
    private final PlantSpeciesService plantSpeciesService;
    private final GrowingLocationService growingLocationService;
    private final PlantCommentService plantCommentService;

    public PlantDoctorService(PlantService plantService,
                              PlantSpeciesService plantSpeciesService,
                              GrowingLocationService growingLocationService, PlantCommentService plantCommentService) {
        this.plantService = plantService;
        this.plantSpeciesService = plantSpeciesService;
        this.growingLocationService = growingLocationService;
        this.plantCommentService = plantCommentService;
    }

    @Transactional
    public PlantDTO createPlant(SimplePlantDTO simplePlantDTO) {
        PlantSpeciesDTO plantSpecies =
                plantSpeciesService.getOrCreatePlantSpeciesByName(simplePlantDTO.plantSpeciesName());
        GrowingLocationDTO growingLocation =
                growingLocationService.getOrCreateGrowingLocationByName(simplePlantDTO.growingLocationName());

        log.debug("Creating a new plant with species {} and location {}",
                plantSpecies.name(), growingLocation.name());
        PlantDTO plant = plantService.createPlant(PlantDTO.builder()
                .plantingDate(simplePlantDTO.plantingDate())
                .plantSpeciesId(plantSpecies.id())
                .growingLocationId(growingLocation.id())
                .build());

        if (StringUtils.hasText(simplePlantDTO.plantComment())) {
            PlantComment plantComment = plantCommentService.addComment(plant.id(), simplePlantDTO.plantComment());
            log.debug("Added a comment to the plant: {}", plantComment);
        }

        return plant;
    }
}
