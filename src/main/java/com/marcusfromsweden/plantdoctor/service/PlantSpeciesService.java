package com.marcusfromsweden.plantdoctor.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import com.marcusfromsweden.plantdoctor.entity.PlantSpecies;
import com.marcusfromsweden.plantdoctor.repository.PlantSpeciesRepository;

@Service
public class PlantSpeciesService {

    private final PlantSpeciesRepository plantSpeciesRepository;

    public List<PlantSpecies> getAllPlantSpecies() {
        return plantSpeciesRepository.findAll();
    }

    public Optional<PlantSpecies> getPlantSpeciesById(Long id) {
        return plantSpeciesRepository.findById(id);
    }

    public PlantSpeciesService(PlantSpeciesRepository plantSpeciesRepository) {
        this.plantSpeciesRepository = plantSpeciesRepository;
    }

    public PlantSpecies createPlantSpecies(PlantSpecies plantSpecies) {
        return plantSpeciesRepository.save(plantSpecies);
    }

    public PlantSpecies updatePlantSpecies(Long id, PlantSpecies plantSpecies) {
        Optional<PlantSpecies> optionalPlantSpecies = plantSpeciesRepository.findById(id);
        if (optionalPlantSpecies.isPresent()) {
            PlantSpecies existingPlantSpecies = optionalPlantSpecies.get();
            existingPlantSpecies.setName(plantSpecies.getName());
            existingPlantSpecies.setDescription(plantSpecies.getDescription());
            return plantSpeciesRepository.save(existingPlantSpecies);
        } else {
            throw new RuntimeException("PlantSpecies not found with id " + id);
        }
    }

    public void deletePlantSpecies(Long id) {
        plantSpeciesRepository.deleteById(id);
    }
}
