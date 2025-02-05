package com.marcusfromsweden.plantdoctor.controller;

import com.marcusfromsweden.plantdoctor.dto.PlantDTO;
import com.marcusfromsweden.plantdoctor.entity.PlantComment;
import com.marcusfromsweden.plantdoctor.service.PlantCommentService;
import com.marcusfromsweden.plantdoctor.service.PlantService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/plants")
public class PlantController {

    private static final Logger log = LoggerFactory.getLogger(PlantController.class);

    private final PlantService plantService;
    private final PlantCommentService plantCommentService;

    public PlantController(PlantService plantService,
                           PlantCommentService plantCommentService) {
        this.plantService = plantService;
        this.plantCommentService = plantCommentService;
    }

    @GetMapping
    public ResponseEntity<List<PlantDTO>> getAllPlants() {
        log.warn("Getting all plants");
        List<PlantDTO> plants = plantService.getAllPlants();
        return new ResponseEntity<>(plants, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlantDTO> getPlantById(@PathVariable Long id) {
        Optional<PlantDTO> plant = plantService.getPlantById(id);
        return plant.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<PlantDTO> createPlant(@Valid @RequestBody PlantDTO plantDTO) {
        PlantDTO createdPlant = plantService.createPlant(plantDTO);
        return new ResponseEntity<>(createdPlant, HttpStatus.CREATED);
    }

    @PostMapping("/{id}/comments")
    public ResponseEntity<Void> addComment(@PathVariable Long id, @RequestParam String comment) {
        plantCommentService.addComment(id, comment);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/{id}/comments")
    public ResponseEntity<List<PlantComment>> getCommentsByPlantId(@PathVariable Long id) {
        List<PlantComment> comments = plantCommentService.getCommentsByPlantId(id);
        return new ResponseEntity<>(comments, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PlantDTO> updatePlant(@PathVariable Long id, @Valid @RequestBody PlantDTO plantDTO) {
        PlantDTO updatedPlant = plantService.updatePlant(id, plantDTO);
        return new ResponseEntity<>(updatedPlant, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePlant(@PathVariable Long id) {
        plantService.deletePlant(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}