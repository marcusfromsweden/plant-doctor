package com.marcusfromsweden.plantdoctor.service;

import com.marcusfromsweden.plantdoctor.dto.BotanicalSpeciesDTO;
import com.marcusfromsweden.plantdoctor.util.PostgresTestContainerTest;
import com.marcusfromsweden.plantdoctor.util.RepositoryTestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BotanicalSpeciesService_VerifyCRUDIT extends PostgresTestContainerTest {

    public static final String BOTANICAL_SPECIES_1_NAME = "Botanical Species 1";
    public static final String BOTANICAL_SPECIES_1_DESCRIPTION = "Some description 1";
    public static final int BOTANICAL_SPECIES_1_ESTIMATED_DAYS_TO_GERMINATION = 7;

    public static final String BOTANICAL_SPECIES_2_NAME = "Botanical Species 2";
    public static final String BOTANICAL_SPECIES_2_DESCRIPTION = "Some description 2";
    public static final int BOTANICAL_SPECIES_2_ESTIMATED_DAYS_TO_GERMINATION = 7;
    public static final String BOTANICAL_SPECIES_2_NAME_UPDATED = "Botanical Species 2.1";
    public static final String BOTANICAL_SPECIES_2_DESCRIPTION_UPDATED = "Some description 2.2";
    public static final int BOTANICAL_SPECIES_2_ESTIMATED_DAYS_TO_GERMINATION_UPDATED = 11;

    @Autowired
    private BotanicalSpeciesService botanicalSpeciesService;
    @Autowired
    private RepositoryTestHelper repositoryTestHelper;

    @BeforeEach
    public void setUp() {
        repositoryTestHelper.deleteAllData();
    }

    @Test
    public void testCreateAndReadAndDelete() {
        BotanicalSpeciesDTO botanicalSpeciesDetails = BotanicalSpeciesDTO.builder()
                .latinName(BOTANICAL_SPECIES_1_NAME)
                .description(BOTANICAL_SPECIES_1_DESCRIPTION)
                .estimatedDaysToGermination(BOTANICAL_SPECIES_1_ESTIMATED_DAYS_TO_GERMINATION)
                .build();

        assertEquals(0, botanicalSpeciesService.getAllBotanicalSpecies().size());

        BotanicalSpeciesDTO botanicalSpecies = botanicalSpeciesService.createBotanicalSpecies(botanicalSpeciesDetails);
        assertNotNull(botanicalSpecies);
        assertNotNull(botanicalSpecies.id());
        assertNotNull(botanicalSpeciesService.getBotanicalSpeciesById(botanicalSpecies.id()));
        assertEquals(1, botanicalSpeciesService.getAllBotanicalSpecies().size());
        assertEquals(botanicalSpeciesDetails.latinName(),
                     botanicalSpecies.latinName());
        assertEquals(botanicalSpeciesDetails.description(),
                     botanicalSpecies.description());
        assertEquals(botanicalSpeciesDetails.estimatedDaysToGermination(),
                     botanicalSpecies.estimatedDaysToGermination());

        botanicalSpeciesService.deleteBotanicalSpecies(botanicalSpecies.id());
        assertEquals(0, botanicalSpeciesService.getAllBotanicalSpecies().size());
    }

    @Test
    public void testCreateAndUpdate() {
        BotanicalSpeciesDTO botanicalSpeciesDetails = BotanicalSpeciesDTO.builder()
                .latinName(BOTANICAL_SPECIES_2_NAME)
                .description(BOTANICAL_SPECIES_2_DESCRIPTION)
                .estimatedDaysToGermination(BOTANICAL_SPECIES_2_ESTIMATED_DAYS_TO_GERMINATION)
                .build();

        BotanicalSpeciesDTO botanicalSpecies =
                botanicalSpeciesService.createBotanicalSpecies(botanicalSpeciesDetails);

        BotanicalSpeciesDTO updatedBotanicalSpeciesDetails = BotanicalSpeciesDTO.builder()
                .latinName(BOTANICAL_SPECIES_2_NAME_UPDATED)
                .description(BOTANICAL_SPECIES_2_DESCRIPTION_UPDATED)
                .estimatedDaysToGermination(BOTANICAL_SPECIES_2_ESTIMATED_DAYS_TO_GERMINATION_UPDATED)
                .build();
        BotanicalSpeciesDTO updatedBotanicalSpecies = botanicalSpeciesService.updateBotanicalSpecies(botanicalSpecies.id(), updatedBotanicalSpeciesDetails);

        assertEquals(updatedBotanicalSpeciesDetails.latinName(),
                     updatedBotanicalSpecies.latinName());
        assertEquals(updatedBotanicalSpeciesDetails.description(),
                     updatedBotanicalSpecies.description());
        assertEquals(updatedBotanicalSpeciesDetails.estimatedDaysToGermination(),
                     updatedBotanicalSpecies.estimatedDaysToGermination());
    }
}
