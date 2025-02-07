package com.marcusfromsweden.plantdoctor.service;

import com.marcusfromsweden.plantdoctor.dto.PlantDTO;
import com.marcusfromsweden.plantdoctor.entity.GrowingLocation;
import com.marcusfromsweden.plantdoctor.entity.Plant;
import com.marcusfromsweden.plantdoctor.entity.SeedPackage;
import com.marcusfromsweden.plantdoctor.repository.GrowingLocationRepository;
import com.marcusfromsweden.plantdoctor.repository.PlantRepository;
import com.marcusfromsweden.plantdoctor.repository.SeedPackageRepository;
import com.marcusfromsweden.plantdoctor.util.PlantMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PlantService {

    private final PlantRepository plantRepository;
    private final SeedPackageRepository seedPackageRepository;
    private final GrowingLocationRepository growingLocationRepository;
    private final PlantMapper plantMapper;

    @Autowired
    public PlantService(PlantRepository plantRepository,
                        SeedPackageRepository seedPackageRepository,
                        GrowingLocationRepository growingLocationRepository,
                        PlantMapper plantMapper) {
        this.plantRepository = plantRepository;
        this.seedPackageRepository = seedPackageRepository;
        this.growingLocationRepository = growingLocationRepository;
        this.plantMapper = plantMapper;
    }

    public List<PlantDTO> getAllPlants() {
        return plantRepository.findAll().stream()
                .map(plantMapper::toDTO)
                .collect(Collectors.toList());
    }

    public Optional<PlantDTO> getPlantById(Long id) {
        return plantRepository.findById(id)
                .map(plantMapper::toDTO);
    }

    public PlantDTO createPlant(PlantDTO plantDTO) {
        Plant plant = new Plant();

        //todo: add validation of DTOs

        //todo: add entity specific exception and update below
        SeedPackage seedPackage = seedPackageRepository.findById(plantDTO.seedPackageId())
                .orElseThrow(() -> new RuntimeException("SeedPackage not found with ID: " + plantDTO.seedPackageId()));
        plant.setSeedPackage(seedPackage);

        GrowingLocation growingLocation = growingLocationRepository.findById(plantDTO.growingLocationId())
                .orElseThrow(() -> new RuntimeException("GrowingLocation not found with ID: " + plantDTO.growingLocationId()));
        plant.setGrowingLocation(growingLocation);

        // Set other fields
        plant.setPlantingDate(plantDTO.plantingDate());
        plant.setGerminationDate(plantDTO.germinationDate());

        Plant savedPlant = plantRepository.save(plant);
        return plantMapper.toDTO(savedPlant);
    }

    public PlantDTO updatePlant(Long plantId,
                                PlantDTO plantDTO) {
        Plant plant = plantRepository.findById(plantId).orElseThrow(() -> new RuntimeException("Plant not found with id " + plantId));

        //todo use entity related exceptions
        SeedPackage seedPackage = seedPackageRepository.findById(plantDTO.seedPackageId())
                .orElseThrow(() -> new RuntimeException("SeedPackage not found with ID: " + plantDTO.seedPackageId()));
        plant.setSeedPackage(seedPackage);

        GrowingLocation growingLocation = growingLocationRepository.findById(plantDTO.growingLocationId())
                .orElseThrow(() -> new RuntimeException("GrowingLocation not found with ID: " + plantDTO.growingLocationId()));
        plant.setGrowingLocation(growingLocation);

        // Update other fields
        plant.setPlantingDate(plantDTO.plantingDate());
        plant.setGerminationDate(plantDTO.germinationDate());

        Plant updatedPlant = plantRepository.save(plant);
        return plantMapper.toDTO(updatedPlant);
    }

    public Plant getPlantEntityByIdOrThrow(Long id) {
        //todo use entity related exceptions
        return plantRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Plant not found with id " + id));
    }

    public void deletePlant(Long id) {
        plantRepository.deleteById(id);
    }
}