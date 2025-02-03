package com.marcusfromsweden.plantdoctor.service;

import com.marcusfromsweden.plantdoctor.dto.PlantSpeciesDTO;
import com.marcusfromsweden.plantdoctor.entity.PlantSpecies;
import com.marcusfromsweden.plantdoctor.repository.PlantSpeciesRepository;
import com.marcusfromsweden.plantdoctor.util.PlantSpeciesMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PlantSpeciesService {

    private final PlantSpeciesRepository plantSpeciesRepository;

    public PlantSpeciesService(PlantSpeciesRepository plantSpeciesRepository) {
        this.plantSpeciesRepository = plantSpeciesRepository;
    }

    public List<PlantSpeciesDTO> getAllPlantSpecies() {
        return plantSpeciesRepository.findAll().stream()
                .map(PlantSpeciesMapper::toDTO)
                .collect(Collectors.toList());
    }

    public Optional<PlantSpeciesDTO> getPlantSpeciesById(Long id) {
        return plantSpeciesRepository.findById(id)
                .map(PlantSpeciesMapper::toDTO);
    }

    public PlantSpeciesDTO createPlantSpecies(PlantSpeciesDTO plantSpeciesDTO) {
        PlantSpecies plantSpecies = PlantSpeciesMapper.toEntity(plantSpeciesDTO);
        PlantSpecies createdPlantSpecies = plantSpeciesRepository.save(plantSpecies);
        return PlantSpeciesMapper.toDTO(createdPlantSpecies);
    }

    public PlantSpeciesDTO updatePlantSpecies(Long id, PlantSpeciesDTO plantSpeciesDTO) {
        Optional<PlantSpecies> optionalPlantSpecies = plantSpeciesRepository.findById(id);
        if (optionalPlantSpecies.isPresent()) {
            PlantSpecies existingPlantSpecies = optionalPlantSpecies.get();
            existingPlantSpecies.setName(plantSpeciesDTO.name());
            existingPlantSpecies.setDescription(plantSpeciesDTO.description());
            existingPlantSpecies.setEstimatedDaysToGermination(plantSpeciesDTO.estimatedDaysToGermination());
            PlantSpecies updatedPlantSpecies = plantSpeciesRepository.save(existingPlantSpecies);
            return PlantSpeciesMapper.toDTO(updatedPlantSpecies);
        } else {
            throw new RuntimeException("PlantSpecies not found with id " + id);
        }
    }

    public void deletePlantSpecies(Long id) {
        plantSpeciesRepository.deleteById(id);
    }
}