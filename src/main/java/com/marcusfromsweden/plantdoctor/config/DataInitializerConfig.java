package com.marcusfromsweden.plantdoctor.config;

import com.marcusfromsweden.plantdoctor.entity.GrowingLocation;
import com.marcusfromsweden.plantdoctor.entity.Plant;
import com.marcusfromsweden.plantdoctor.entity.PlantSpecies;
import com.marcusfromsweden.plantdoctor.repository.GrowingLocationRepository;
import com.marcusfromsweden.plantdoctor.repository.PlantRepository;
import com.marcusfromsweden.plantdoctor.repository.PlantSpeciesRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;

@Configuration
public class DataInitializerConfig {

    private final PlantSpeciesRepository plantSpeciesRepository;
    private final GrowingLocationRepository growingLocationRepository;
    private final PlantRepository plantRepository;

    public DataInitializerConfig(PlantSpeciesRepository plantSpeciesRepository, GrowingLocationRepository growingLocationRepository, PlantRepository plantRepository) {
        this.plantSpeciesRepository = plantSpeciesRepository;
        this.growingLocationRepository = growingLocationRepository;
        this.plantRepository = plantRepository;
    }

    @Bean
    public CommandLineRunner dataInitializer() {
        return args -> {
            deleteTableData();

            // Add initial data for PlantSpecies
            PlantSpecies regaularBasil = new PlantSpecies();
            regaularBasil.setName("Basil");
            regaularBasil.setDescription("Regular basil of the mint family.");

            PlantSpecies favouriteRadish = new PlantSpecies();
            favouriteRadish.setName("Radish");
            favouriteRadish.setDescription("A root vegetable of the Brassicaceae family.");

            plantSpeciesRepository.save(regaularBasil);
            plantSpeciesRepository.save(favouriteRadish);

            // Add initial data for GrowingLocation
            GrowingLocation pot1 = new GrowingLocation();
            pot1.setLocationName("Pot 1");
            pot1.setOccupied(false);

            GrowingLocation pot2 = new GrowingLocation();
            pot2.setLocationName("Pot 2");
            pot2.setOccupied(false);

            growingLocationRepository.save(pot1);
            growingLocationRepository.save(pot2);

            // Add initial data for Plant
            Plant rosePlant = new Plant();
            rosePlant.setPlantSpecies(regaularBasil);
            rosePlant.setGrowingLocation(pot1);
            rosePlant.setComment("This is a comment.");
            rosePlant.setPlantingDate(LocalDate.parse("2021-01-01"));
            rosePlant.setGerminationDate(LocalDate.parse("2021-01-20"));

            Plant tulipPlant = new Plant();
            tulipPlant.setPlantSpecies(favouriteRadish);
            tulipPlant.setGrowingLocation(pot2);
            tulipPlant.setComment("This is another comment.");
            tulipPlant.setPlantingDate(LocalDate.parse("2021-01-11"));
            tulipPlant.setGerminationDate(LocalDate.parse("2021-01-30"));

            plantRepository.save(rosePlant);
            plantRepository.save(tulipPlant);
        };
    }

    private void deleteTableData() {
        plantRepository.deleteAll();
        plantSpeciesRepository.deleteAll();
        growingLocationRepository.deleteAll();
    }
}