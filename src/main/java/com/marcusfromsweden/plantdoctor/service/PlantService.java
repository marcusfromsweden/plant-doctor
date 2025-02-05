package com.marcusfromsweden.plantdoctor.service;

import com.marcusfromsweden.plantdoctor.dto.PlantDTO;
import com.marcusfromsweden.plantdoctor.entity.GrowingLocation;
import com.marcusfromsweden.plantdoctor.entity.Plant;
import com.marcusfromsweden.plantdoctor.entity.PlantSpecies;
import com.marcusfromsweden.plantdoctor.repository.GrowingLocationRepository;
import com.marcusfromsweden.plantdoctor.repository.PlantRepository;
import com.marcusfromsweden.plantdoctor.repository.PlantSpeciesRepository;
import com.marcusfromsweden.plantdoctor.util.PlantMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PlantService {

    private final PlantRepository plantRepository;
    private final PlantSpeciesRepository plantSpeciesRepository;
    private final GrowingLocationRepository growingLocationRepository;
    private final PlantMapper plantMapper;

    @Autowired
    public PlantService(PlantRepository plantRepository,
                        PlantSpeciesRepository plantSpeciesRepository,
                        GrowingLocationRepository growingLocationRepository,
                        PlantMapper plantMapper) {
        this.plantRepository = plantRepository;
        this.plantSpeciesRepository = plantSpeciesRepository;
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

        // Set plant species
        Optional<PlantSpecies> optionalPlantSpecies = plantSpeciesRepository.findById(plantDTO.plantSpeciesId());
        optionalPlantSpecies.ifPresent(plant::setPlantSpecies);

        // Set growing location
        Optional<GrowingLocation> optionalGrowingLocation = growingLocationRepository.findById(plantDTO.growingLocationId());
        optionalGrowingLocation.ifPresent(plant::setGrowingLocation);

        // Set other fields
        plant.setPlantingDate(plantDTO.plantingDate());
        plant.setGerminationDate(plantDTO.germinationDate());

        Plant savedPlant = plantRepository.save(plant);
        return plantMapper.toDTO(savedPlant);
    }

    public PlantDTO updatePlant(Long plantId, PlantDTO plantDTO) {
        Optional<Plant> optionalPlant = plantRepository.findById(plantId);
        if (optionalPlant.isPresent()) {
            Plant plant = optionalPlant.get();

            // Update plant species
            Optional<PlantSpecies> optionalPlantSpecies = plantSpeciesRepository.findById(plantDTO.plantSpeciesId());
            optionalPlantSpecies.ifPresent(plant::setPlantSpecies);

            // Update growing location
            Optional<GrowingLocation> optionalGrowingLocation = growingLocationRepository.findById(plantDTO.growingLocationId());
            optionalGrowingLocation.ifPresent(plant::setGrowingLocation);

            // Update other fields
            plant.setPlantingDate(plantDTO.plantingDate());
            plant.setGerminationDate(plantDTO.germinationDate());

            Plant updatedPlant = plantRepository.save(plant);
            return plantMapper.toDTO(updatedPlant);
        } else {
            throw new RuntimeException("Plant not found with id " + plantId);
        }
    }
    
    public Plant getPlantEntityByIdOrThrow(Long id) {
        return plantRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Plant not found with id " + id));
    }

    public void deletePlant(Long id) {
        plantRepository.deleteById(id);
    }
}