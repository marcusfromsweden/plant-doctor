package com.marcusfromsweden.plantdoctor.controller.internal;

import com.marcusfromsweden.plantdoctor.dto.PlantSpeciesDTO;
import com.marcusfromsweden.plantdoctor.service.PlantSpeciesService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/internal/plant-species")
public class PlantSpeciesController {

    private final PlantSpeciesService plantSpeciesService;

    public PlantSpeciesController(PlantSpeciesService plantSpeciesService) {
        this.plantSpeciesService = plantSpeciesService;
    }

    @GetMapping
    public ResponseEntity<List<PlantSpeciesDTO>> getAllPlantSpecies() {
        List<PlantSpeciesDTO> plantSpeciesDTOList = plantSpeciesService.getAllPlantSpecies();
        return new ResponseEntity<>(plantSpeciesDTOList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlantSpeciesDTO> getPlantSpeciesById(@PathVariable Long id) {
        Optional<PlantSpeciesDTO> plantSpeciesDTO = plantSpeciesService.getPlantSpeciesById(id);
        return plantSpeciesDTO.map(dto -> new ResponseEntity<>(dto, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<PlantSpeciesDTO> createPlantSpecies(
            @Valid @RequestBody PlantSpeciesDTO plantSpeciesDTO) {
        PlantSpeciesDTO createdPlantSpeciesDTO = plantSpeciesService.createPlantSpecies(plantSpeciesDTO);
        return new ResponseEntity<>(createdPlantSpeciesDTO, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PlantSpeciesDTO> updatePlantSpecies(@PathVariable Long id,
                                                              @Valid @RequestBody PlantSpeciesDTO plantSpeciesDTO) {
        PlantSpeciesDTO updatedPlantSpeciesDTO = plantSpeciesService.updatePlantSpecies(id, plantSpeciesDTO);
        return new ResponseEntity<>(updatedPlantSpeciesDTO, HttpStatus.OK);
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<PlantSpeciesDTO> getPlantSpeciesByName(@PathVariable String name) {
        Optional<PlantSpeciesDTO> plantSpeciesDTO = plantSpeciesService.getPlantSpeciesByName(name);
        return plantSpeciesDTO.map(dto -> new ResponseEntity<>(dto, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePlantSpecies(@PathVariable Long id) {
        plantSpeciesService.deletePlantSpecies(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}