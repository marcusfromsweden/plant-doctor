package com.marcusfromsweden.plantdoctor.service;

import com.marcusfromsweden.plantdoctor.dto.GrowingLocationDTO;
import com.marcusfromsweden.plantdoctor.dto.mapper.GrowingLocationMapper;
import com.marcusfromsweden.plantdoctor.util.GrowingLocationTestHelper;
import com.marcusfromsweden.plantdoctor.util.PostgresTestContainerTest;
import com.marcusfromsweden.plantdoctor.util.RepositoryTestHelper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import({GrowingLocationTestHelper.class, GrowingLocationMapper.class})
public class GrowingLocationServiceCRUDIT extends PostgresTestContainerTest {

    @Autowired
    private GrowingLocationService growingLocationService;
    @Autowired
    private GrowingLocationTestHelper growingLocationTestHelper;
    @Autowired
    private RepositoryTestHelper repositoryTestHelper;

    private static final String GROWING_LOCATION_1_NAME = "Pot 1";
    private static final String GROWING_LOCATION_1_NAME_UPDATED = "Pot 123";
    private static final String GROWING_LOCATION_2_NAME = "Pot 2";

    @Test
    public void shouldCreateAndDelete() {
        repositoryTestHelper.deleteAllData();
        assertEquals(0, growingLocationService.getAllGrowingLocations().size());

        GrowingLocationDTO growingLocationDTO =
                growingLocationTestHelper.createDTO(null, GROWING_LOCATION_2_NAME);
        GrowingLocationDTO createdGrowingLocationDTO =
                growingLocationService.createGrowingLocation(growingLocationDTO);
        assertNotNull(createdGrowingLocationDTO);
        assertNotNull(createdGrowingLocationDTO.id());
        assertTrue(growingLocationService.getGrowingLocationById(createdGrowingLocationDTO.id()).isPresent());
        assertEquals(1, growingLocationService.getAllGrowingLocations().size());
        assertEquals(growingLocationDTO.name(), createdGrowingLocationDTO.name());

        growingLocationService.deleteGrowingLocation(createdGrowingLocationDTO.id());
        assertEquals(0, growingLocationService.getAllGrowingLocations().size());
    }

    @Test
    public void shouldCreateAndUpdate() {
        GrowingLocationDTO growingLocationDTO =
                growingLocationTestHelper.createDTO(null, GROWING_LOCATION_1_NAME);
        GrowingLocationDTO createdGrowingLocationDTO =
                growingLocationService.createGrowingLocation(growingLocationDTO);

        GrowingLocationDTO growingLocationForUpdateDTO = growingLocationTestHelper.createDTO(
                createdGrowingLocationDTO.id(),
                GROWING_LOCATION_1_NAME_UPDATED
        );
        GrowingLocationDTO updatedGrowingLocationDTO =
                growingLocationService.updateGrowingLocation(createdGrowingLocationDTO.id(), growingLocationForUpdateDTO);

        assertNotNull(updatedGrowingLocationDTO);
        assertEquals(createdGrowingLocationDTO.id(), updatedGrowingLocationDTO.id());
        assertEquals(GROWING_LOCATION_1_NAME_UPDATED, updatedGrowingLocationDTO.name());
    }

}