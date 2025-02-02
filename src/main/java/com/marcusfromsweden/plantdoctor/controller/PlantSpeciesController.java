package com.marcusfromsweden.plantdoctor.controller;

import com.marcusfromsweden.plantdoctor.entity.PlantSpecies;
import com.marcusfromsweden.plantdoctor.service.PlantSpeciesService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/plant-species")
public class PlantSpeciesController {

    private final PlantSpeciesService plantSpeciesService;

    public PlantSpeciesController(PlantSpeciesService plantSpeciesService) {
        this.plantSpeciesService = plantSpeciesService;
    }

    @GetMapping
    public ResponseEntity<List<PlantSpecies>> getAllPlantSpecies() {
        List<PlantSpecies> plantSpeciesList = plantSpeciesService.getAllPlantSpecies();
        return new ResponseEntity<>(plantSpeciesList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlantSpecies> getPlantSpeciesById(@PathVariable Long id) {
        Optional<PlantSpecies> plantSpecies = plantSpeciesService.getPlantSpeciesById(id);
        return plantSpecies.map(species -> new ResponseEntity<>(species, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<PlantSpecies> createPlantSpecies(
            @Valid @RequestBody PlantSpecies plantSpecies) {
        PlantSpecies createdPlantSpecies = plantSpeciesService.createPlantSpecies(plantSpecies);
        return new ResponseEntity<>(createdPlantSpecies, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PlantSpecies> updatePlantSpecies(@PathVariable Long id,
                                                           @Valid @RequestBody PlantSpecies plantSpecies) {
        PlantSpecies updatedPlantSpecies = plantSpeciesService.updatePlantSpecies(id, plantSpecies);
        return new ResponseEntity<>(updatedPlantSpecies, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePlantSpecies(@PathVariable Long id) {
        plantSpeciesService.deletePlantSpecies(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
