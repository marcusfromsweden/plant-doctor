package com.marcusfromsweden.plantdoctor.controller;

import com.marcusfromsweden.plantdoctor.dto.PlantSpeciesDTO;
import com.marcusfromsweden.plantdoctor.entity.PlantSpecies;
import com.marcusfromsweden.plantdoctor.service.PlantSpeciesService;
import com.marcusfromsweden.plantdoctor.util.PlantSpeciesMapper;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/plant-species")
public class PlantSpeciesController {

    private final PlantSpeciesService plantSpeciesService;

    public PlantSpeciesController(PlantSpeciesService plantSpeciesService) {
        this.plantSpeciesService = plantSpeciesService;
    }

    @GetMapping
    public ResponseEntity<List<PlantSpeciesDTO>> getAllPlantSpecies() {
        List<PlantSpecies> plantSpeciesList = plantSpeciesService.getAllPlantSpecies();
        List<PlantSpeciesDTO> plantSpeciesDTOList = plantSpeciesList.stream()
                .map(PlantSpeciesMapper::toDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(plantSpeciesDTOList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlantSpeciesDTO> getPlantSpeciesById(@PathVariable Long id) {
        Optional<PlantSpecies> plantSpecies = plantSpeciesService.getPlantSpeciesById(id);
        return plantSpecies.map(species -> new ResponseEntity<>(PlantSpeciesMapper.toDTO(species), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<PlantSpeciesDTO> createPlantSpecies(
            @Valid @RequestBody PlantSpeciesDTO plantSpeciesDTO) {
        PlantSpecies plantSpecies = PlantSpeciesMapper.toEntity(plantSpeciesDTO);
        PlantSpecies createdPlantSpecies = plantSpeciesService.createPlantSpecies(plantSpecies);
        return new ResponseEntity<>(PlantSpeciesMapper.toDTO(createdPlantSpecies), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PlantSpeciesDTO> updatePlantSpecies(@PathVariable Long id,
                                                              @Valid @RequestBody PlantSpeciesDTO plantSpeciesDTO) {
        PlantSpecies plantSpecies = PlantSpeciesMapper.toEntity(plantSpeciesDTO);
        PlantSpecies updatedPlantSpecies = plantSpeciesService.updatePlantSpecies(id, plantSpecies);
        return new ResponseEntity<>(PlantSpeciesMapper.toDTO(updatedPlantSpecies), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePlantSpecies(@PathVariable Long id) {
        plantSpeciesService.deletePlantSpecies(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}