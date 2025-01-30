package com.marcusfromsweden.plantdoctor.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.marcusfromsweden.plantdoctor.entity.GrowingLocation;
import com.marcusfromsweden.plantdoctor.entity.Plant;
import com.marcusfromsweden.plantdoctor.entity.PlantSpecies;
import com.marcusfromsweden.plantdoctor.repository.GrowingLocationRepository;
import com.marcusfromsweden.plantdoctor.repository.PlantRepository;
import com.marcusfromsweden.plantdoctor.repository.PlantSpeciesRepository;

@Service
public class PlantService {

    private final PlantRepository plantRepository;
    private final PlantSpeciesRepository plantSpeciesRepository;
    private final GrowingLocationRepository growingLocationRepository;

    @Autowired
    public PlantService(PlantRepository plantRepository, PlantSpeciesRepository plantSpeciesRepository, GrowingLocationRepository growingLocationRepository) {
        this.plantRepository = plantRepository;
        this.plantSpeciesRepository = plantSpeciesRepository;
        this.growingLocationRepository = growingLocationRepository;
    }

    public List<Plant> getAllPlants() {
        return plantRepository.findAll();
    }

    public Optional<Plant> getPlantById(Long id) {
        return plantRepository.findById(id);
    }

    public Plant addPlant(Long plantSpeciesId, Long growingLocationId, LocalDate plantingDate, LocalDate germinationDate, String comment) {
        Plant plant = new Plant();

        // Set plant species
        Optional<PlantSpecies> optionalPlantSpecies = plantSpeciesRepository.findById(plantSpeciesId);
        optionalPlantSpecies.ifPresent(plant::setPlantSpecies);

        // Set growing location
        Optional<GrowingLocation> optionalGrowingLocation = growingLocationRepository.findById(growingLocationId);
        optionalGrowingLocation.ifPresent(plant::setGrowingLocation);

        // Set other fields
        plant.setPlantingDate(plantingDate);
        plant.setGerminationDate(germinationDate);
        plant.setComment(comment);

        return plantRepository.save(plant);
    }

    public Plant updatePlant(Long plantId, Long plantSpeciesId, Long growingLocationId, LocalDate plantingDate, LocalDate germinationDate, String comment) {
        Optional<Plant> optionalPlant = plantRepository.findById(plantId);
        if (optionalPlant.isPresent()) {
            Plant plant = optionalPlant.get();

            // Update plant species
            Optional<PlantSpecies> optionalPlantSpecies = plantSpeciesRepository.findById(plantSpeciesId);
            optionalPlantSpecies.ifPresent(plant::setPlantSpecies);

            // Update growing location
            Optional<GrowingLocation> optionalGrowingLocation = growingLocationRepository.findById(growingLocationId);
            optionalGrowingLocation.ifPresent(plant::setGrowingLocation);

            // Update other fields
            plant.setPlantingDate(plantingDate);
            plant.setGerminationDate(germinationDate);
            plant.setComment(comment);

            return plantRepository.save(plant);
        } else {
            throw new RuntimeException("Plant not found with id " + plantId);
        }
    }

    public void deletePlant(Long id) {
        plantRepository.deleteById(id);
    }
}
