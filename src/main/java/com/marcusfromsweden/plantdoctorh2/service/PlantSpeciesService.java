package com.marcusfromsweden.plantdoctorh2.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.marcusfromsweden.plantdoctorh2.entity.PlantSpecies;
import com.marcusfromsweden.plantdoctorh2.repository.PlantSpeciesRepository;

@Service
public class PlantSpeciesService {

    @Autowired
    private PlantSpeciesRepository plantSpeciesRepository;

    public List<PlantSpecies> getAllPlantSpecies() {
        return plantSpeciesRepository.findAll();
    }

    public Optional<PlantSpecies> getPlantSpeciesById(Long id) {
        return plantSpeciesRepository.findById(id);
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
