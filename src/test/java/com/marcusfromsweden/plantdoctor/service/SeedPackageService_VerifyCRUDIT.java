package com.marcusfromsweden.plantdoctor.service;

import com.marcusfromsweden.plantdoctor.dto.BotanicalSpeciesDTO;
import com.marcusfromsweden.plantdoctor.dto.SeedPackageDTO;
import com.marcusfromsweden.plantdoctor.entity.SeedPackage;
import com.marcusfromsweden.plantdoctor.util.PostgresTestContainerTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SeedPackageService_VerifyCRUDIT extends PostgresTestContainerTest {

    @Autowired
    private SeedPackageService seedPackageService;
    @Autowired
    private BotanicalSpeciesService botanicalSpeciesService;

    @Test
    public void testCreateAndUpdate() {
        BotanicalSpeciesDTO botanicalSpeciesDetails = BotanicalSpeciesDTO.builder()
                .name("Botanical Species 1")
                .description("Some description")
                .estimatedDaysToGermination(7)
                .build();
        BotanicalSpeciesDTO botanicalSpecies = botanicalSpeciesService.createBotanicalSpecies(botanicalSpeciesDetails);

        SeedPackageDTO seedPackageDetails = SeedPackageDTO.builder()
                .name("SP 1")
                .botanicalSpeciesId(botanicalSpecies.id())
                .numberOfSeeds(100)
                .build();

        SeedPackageDTO seedPackage = seedPackageService.createSeedPackage(seedPackageDetails);

        SeedPackageDTO newSeedPackageDetails = SeedPackageDTO.builder()
                .name("SP 2")
                .botanicalSpeciesId(botanicalSpecies.id())
                .numberOfSeeds(200)
                .build();

        seedPackageService.updateSeedPackage(seedPackage.id(), newSeedPackageDetails);

        SeedPackage updatedSeedPackage = seedPackageService.getSeedPackageEntityByIdOrThrow(seedPackage.id());
        assertEquals(newSeedPackageDetails.name(), updatedSeedPackage.getName());
    }
}
