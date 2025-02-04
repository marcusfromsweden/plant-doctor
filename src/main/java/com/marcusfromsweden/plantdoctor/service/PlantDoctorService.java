package com.marcusfromsweden.plantdoctor.service;

import com.marcusfromsweden.plantdoctor.dto.GrowingLocationDTO;
import com.marcusfromsweden.plantdoctor.dto.PlantDTO;
import com.marcusfromsweden.plantdoctor.dto.PlantSpeciesDTO;
import com.marcusfromsweden.plantdoctor.dto.SimplePlantDTO;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class PlantDoctorService {

    private final Logger log = LoggerFactory.getLogger(PlantDoctorService.class);

    private final PlantService plantService;
    private final PlantSpeciesService plantSpeciesService;
    private final GrowingLocationService growingLocationService;

    public PlantDoctorService(PlantService plantService,
                              PlantSpeciesService plantSpeciesService,
                              GrowingLocationService growingLocationService) {
        this.plantService = plantService;
        this.plantSpeciesService = plantSpeciesService;
        this.growingLocationService = growingLocationService;
    }

    @Transactional
    public PlantDTO createPlant(SimplePlantDTO simplePlantDTO) {
        PlantSpeciesDTO plantSpecies =
                plantSpeciesService.getOrCreatePlantSpeciesByName(simplePlantDTO.plantSpeciesName());
        GrowingLocationDTO growingLocation =
                growingLocationService.getOrCreateGrowingLocationByName(simplePlantDTO.growingLocationName());

        log.debug("Creating a new plant with species {} and location {}",
                plantSpecies.name(), growingLocation.name());
        return plantService.createPlant(PlantDTO.builder()
                .plantingDate(simplePlantDTO.plantingDate())
                .plantSpeciesId(plantSpecies.id())
                .growingLocationId(growingLocation.id())
                .build());
    }
}
