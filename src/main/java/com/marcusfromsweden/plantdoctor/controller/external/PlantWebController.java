package com.marcusfromsweden.plantdoctor.controller.external;

import com.marcusfromsweden.plantdoctor.dto.PlantWebDTO;
import com.marcusfromsweden.plantdoctor.service.GrowingLocationService;
import com.marcusfromsweden.plantdoctor.service.PlantService;
import com.marcusfromsweden.plantdoctor.service.PlantSpeciesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/web/external/plants")
public class PlantWebController {

    private static final Logger log = LoggerFactory.getLogger(PlantWebController.class);

    private final PlantService plantService;
    private final PlantSpeciesService plantSpeciesService;
    private final GrowingLocationService growingLocationService;

    public PlantWebController(PlantService plantService, PlantSpeciesService plantSpeciesService, GrowingLocationService growingLocationService) {
        this.plantService = plantService;
        this.plantSpeciesService = plantSpeciesService;
        this.growingLocationService = growingLocationService;
    }

    @PostMapping
    public ResponseEntity<PlantWebDTO> createPlant(@RequestBody PlantWebDTO plantWebDTO) {
        log.warn("Creating a new plant for web");


        // Implement the logic to create a new plant using PlantWebDTO
        // For now, returning the input DTO
        return new ResponseEntity<>(plantWebDTO, HttpStatus.CREATED);
    }
}
