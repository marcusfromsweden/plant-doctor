package com.marcusfromsweden.plantdoctor.controller;

import com.marcusfromsweden.plantdoctor.dto.PlantDTO;
import com.marcusfromsweden.plantdoctor.dto.QuickCreatePlantDTO;
import com.marcusfromsweden.plantdoctor.service.PlantDoctorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/plant-doctor")
public class ExternalPlantDoctorController {

    private static final Logger log = LoggerFactory.getLogger(ExternalPlantDoctorController.class);

    private final PlantDoctorService plantDoctorService;

    public ExternalPlantDoctorController(PlantDoctorService plantDoctorService) {
        this.plantDoctorService = plantDoctorService;
    }

    @PostMapping
    public ResponseEntity<PlantDTO> createPlant(@RequestBody QuickCreatePlantDTO quickCreatePlantDTO) {
        log.warn("Creating a new plant for web");
        return new ResponseEntity<>(plantDoctorService.quickCreatePlant(quickCreatePlantDTO), HttpStatus.CREATED);
    }
}
