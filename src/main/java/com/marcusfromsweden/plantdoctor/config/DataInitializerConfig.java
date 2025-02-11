package com.marcusfromsweden.plantdoctor.config;

import com.marcusfromsweden.plantdoctor.entity.BotanicalSpecies;
import com.marcusfromsweden.plantdoctor.entity.GrowingLocation;
import com.marcusfromsweden.plantdoctor.entity.Plant;
import com.marcusfromsweden.plantdoctor.entity.PlantComment;
import com.marcusfromsweden.plantdoctor.entity.SeedPackage;
import com.marcusfromsweden.plantdoctor.repository.BotanicalSpeciesRepository;
import com.marcusfromsweden.plantdoctor.repository.GrowingLocationRepository;
import com.marcusfromsweden.plantdoctor.repository.PlantCommentRepository;
import com.marcusfromsweden.plantdoctor.repository.PlantRepository;
import com.marcusfromsweden.plantdoctor.repository.SeedPackageRepository;
import com.marcusfromsweden.plantdoctor.util.CustomProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Configuration
public class DataInitializerConfig {

    private static final Logger log = LoggerFactory.getLogger(DataInitializerConfig.class);

    private final BotanicalSpeciesRepository botanicalSpeciesRepository;
    private final GrowingLocationRepository growingLocationRepository;
    private final PlantRepository plantRepository;
    private final PlantCommentRepository plantCommentRepository;
    private final SeedPackageRepository seedPackageRepository;
    private final CustomProperties customProperties;

    public DataInitializerConfig(BotanicalSpeciesRepository botanicalSpeciesRepository,
                                 GrowingLocationRepository growingLocationRepository,
                                 PlantRepository plantRepository,
                                 PlantCommentRepository plantCommentRepository,
                                 SeedPackageRepository seedPackageRepository,
                                 CustomProperties customProperties) {
        this.botanicalSpeciesRepository = botanicalSpeciesRepository;
        this.growingLocationRepository = growingLocationRepository;
        this.plantRepository = plantRepository;
        this.plantCommentRepository = plantCommentRepository;
        this.seedPackageRepository = seedPackageRepository;
        this.customProperties = customProperties;
    }

    @Bean
    public CommandLineRunner dataInitializer() {
        return args -> {
            if (customProperties.isDeleteAndPopulateTables()) {
                deleteTableData();
                populateTableData();
            } else {
                log.info("Skipping data initialization as delete-and-populate-tables is set to false.");
            }
        };
    }

    private void deleteTableData() {
        log.info("Deleting all data from tables.");
        plantCommentRepository.deleteAll();
        plantRepository.deleteAll();
        seedPackageRepository.deleteAll();
        botanicalSpeciesRepository.deleteAll();
        growingLocationRepository.deleteAll();
        log.info("All data deleted.");
    }

    private void populateTableData() {
        log.info("Adding BotanicalSpecies");
        BotanicalSpecies regularBasil = new BotanicalSpecies();
        regularBasil.setLatinName("Ocimum basilicum");
        regularBasil.setDescription("Regular basil of the mint family.");

        BotanicalSpecies favouriteRadish = new BotanicalSpecies();
        favouriteRadish.setLatinName("Raphanus sativus");
        favouriteRadish.setDescription("A root vegetable of the Brassicaceae family.");

        botanicalSpeciesRepository.save(regularBasil);
        botanicalSpeciesRepository.save(favouriteRadish);

        log.info("Adding SeedPackages");
        SeedPackage basilSeedPackage = new SeedPackage();
        basilSeedPackage.setBotanicalSpecies(regularBasil);
        basilSeedPackage.setName("Basil seeds from the local store");
        basilSeedPackage.setNumberOfSeeds(100);

        seedPackageRepository.save(basilSeedPackage);

        log.info("Adding GrowingLocations");
        GrowingLocation pot1 = new GrowingLocation();
        pot1.setName("Pot 1");

        GrowingLocation pot2 = new GrowingLocation();
        pot2.setName("Pot 2");

        growingLocationRepository.save(pot1);
        growingLocationRepository.save(pot2);

        log.info("Adding Plants");
        Plant basilPlant = new Plant();
        basilPlant.setSeedPackage(basilSeedPackage);
        basilPlant.setGrowingLocation(pot1);
        basilPlant.setPlantingDate(LocalDate.parse("2021-01-01"));
        basilPlant.setGerminationDate(LocalDate.parse("2021-01-20"));

        Plant radishPlant = new Plant();
        radishPlant.setSeedPackage(basilSeedPackage);
        radishPlant.setGrowingLocation(pot2);
        radishPlant.setPlantingDate(LocalDate.parse("2021-01-11"));
        radishPlant.setGerminationDate(LocalDate.parse("2021-01-30"));

        plantRepository.save(basilPlant);
        plantRepository.save(radishPlant);

        log.info("Adding PlantComments");
        PlantComment basilComment1 = new PlantComment();
        basilComment1.setPlant(basilPlant);
        basilComment1.setText("This basil is growing well.");
        basilComment1.setCreatedDate(LocalDateTime.now());
        plantCommentRepository.save(basilComment1);

        log.info("Test data initialized.");
    }
}