package com.marcusfromsweden.plantdoctor.service;

import com.marcusfromsweden.plantdoctor.dto.BotanicalSpeciesDTO;
import com.marcusfromsweden.plantdoctor.util.BotanicalSpeciesTestHelper;
import com.marcusfromsweden.plantdoctor.util.PostgresTestContainerTest;
import com.marcusfromsweden.plantdoctor.util.RepositoryTestHelper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BotanicalSpeciesServiceCRUDIT extends PostgresTestContainerTest {

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

    @Test
    public void shouldCreateAndDelete() {
        repositoryTestHelper.deleteAllData();
        assertEquals(0, botanicalSpeciesService.getAllBotanicalSpecies().size());

        BotanicalSpeciesDTO botanicalSpeciesDetails = BotanicalSpeciesTestHelper.createDTO(null,
                                                                                           BOTANICAL_SPECIES_1_NAME,
                                                                                           BOTANICAL_SPECIES_1_DESCRIPTION,
                                                                                           BOTANICAL_SPECIES_1_ESTIMATED_DAYS_TO_GERMINATION);

        BotanicalSpeciesDTO botanicalSpecies = botanicalSpeciesService.createBotanicalSpecies(botanicalSpeciesDetails);
        assertNotNull(botanicalSpecies);
        assertNotNull(botanicalSpecies.id());
        assertTrue(botanicalSpeciesService.getBotanicalSpeciesById(botanicalSpecies.id()).isPresent());
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
    public void shouldCreateAndUpdate() {
        BotanicalSpeciesDTO botanicalSpeciesDTO =
                BotanicalSpeciesTestHelper.createDTO(null,
                                                     BOTANICAL_SPECIES_2_NAME,
                                                     BOTANICAL_SPECIES_2_DESCRIPTION,
                                                     BOTANICAL_SPECIES_2_ESTIMATED_DAYS_TO_GERMINATION);
        BotanicalSpeciesDTO botanicalSpecies =
                botanicalSpeciesService.createBotanicalSpecies(botanicalSpeciesDTO);

        BotanicalSpeciesDTO updatedBotanicalSpeciesDTO =
                BotanicalSpeciesTestHelper.createDTO(null,
                                                     BOTANICAL_SPECIES_2_NAME_UPDATED,
                                                     BOTANICAL_SPECIES_2_DESCRIPTION_UPDATED,
                                                     BOTANICAL_SPECIES_2_ESTIMATED_DAYS_TO_GERMINATION_UPDATED);
        BotanicalSpeciesDTO updatedBotanicalSpecies =
                botanicalSpeciesService.updateBotanicalSpecies(botanicalSpecies.id(), updatedBotanicalSpeciesDTO);

        assertEquals(updatedBotanicalSpeciesDTO.latinName(),
                     updatedBotanicalSpecies.latinName());
        assertEquals(updatedBotanicalSpeciesDTO.description(),
                     updatedBotanicalSpecies.description());
        assertEquals(updatedBotanicalSpeciesDTO.estimatedDaysToGermination(),
                     updatedBotanicalSpecies.estimatedDaysToGermination());
    }

}
