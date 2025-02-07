package com.marcusfromsweden.plantdoctor.util;

import com.marcusfromsweden.plantdoctor.repository.GrowingLocationRepository;
import com.marcusfromsweden.plantdoctor.repository.PlantCommentRepository;
import com.marcusfromsweden.plantdoctor.repository.PlantRepository;
import com.marcusfromsweden.plantdoctor.repository.PlantSpeciesRepository;
import com.marcusfromsweden.plantdoctor.repository.SeedPackageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class RepositoryTestHelper {

    private final Logger log = LoggerFactory.getLogger(RepositoryTestHelper.class);

    private final PlantRepository plantRepository;
    private final PlantCommentRepository plantCommentRepository;
    private final GrowingLocationRepository growingLocationRepository;
    private final PlantSpeciesRepository plantSpeciesRepository;
    private final SeedPackageRepository seedPackageRepository;

    public RepositoryTestHelper(PlantRepository plantRepository,
                                PlantCommentRepository plantCommentRepository,
                                GrowingLocationRepository growingLocationRepository,
                                PlantSpeciesRepository plantSpeciesRepository,
                                SeedPackageRepository seedPackageRepository) {
        this.plantRepository = plantRepository;
        this.plantCommentRepository = plantCommentRepository;
        this.growingLocationRepository = growingLocationRepository;
        this.plantSpeciesRepository = plantSpeciesRepository;
        this.seedPackageRepository = seedPackageRepository;
    }

    @SuppressWarnings("unused")
    public void logEntityCounts(String header) {
        log.info("{}", header);
        log.info("  Plant count:            {}", plantRepository.count());
        log.info("  Plant comment count:    {}", plantCommentRepository.count());
        log.info("  Growing location count: {}", growingLocationRepository.count());
        log.info("  Plant species count:    {}", plantSpeciesRepository.count());
        log.info("  Seed package count:     {}", seedPackageRepository.count());
    }

    public void deleteAllData() {
        plantCommentRepository.deleteAll();
        plantRepository.deleteAll();
        growingLocationRepository.deleteAll();
        seedPackageRepository.deleteAll();
        plantSpeciesRepository.deleteAll();
    }
}
