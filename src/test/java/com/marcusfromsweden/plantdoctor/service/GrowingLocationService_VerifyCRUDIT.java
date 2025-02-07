package com.marcusfromsweden.plantdoctor.service;

import com.marcusfromsweden.plantdoctor.dto.GrowingLocationDTO;
import com.marcusfromsweden.plantdoctor.util.PostgresTestContainerTest;
import com.marcusfromsweden.plantdoctor.util.RepositoryTestHelper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class GrowingLocationService_VerifyCRUDIT extends PostgresTestContainerTest {

    @Autowired
    private GrowingLocationService growingLocationService;
    @Autowired
    private RepositoryTestHelper repositoryTestHelper;

    private static final String GROWING_LOCATION_1_NAME = "Pot 1";
    private static final String GROWING_LOCATION_1_NAME_UPDATE = "Pot 11";
    private static final String GROWING_LOCATION_2_NAME = "Pot 2";
    private static final String GROWING_LOCATION_3_NAME = "Pot 3";

    @Test
    public void testCreateAndRead() {
        GrowingLocationDTO growingLocationDTOForCreateAndRead = GrowingLocationDTO.builder()
                .name(GROWING_LOCATION_1_NAME)
                .occupied(false)
                .build();

        GrowingLocationDTO growingLocation = growingLocationService.createGrowingLocation(growingLocationDTOForCreateAndRead);

        Optional<GrowingLocationDTO> createdGrowingLocation =
                growingLocationService.getGrowingLocationById(growingLocation.id());

        assertTrue(createdGrowingLocation.isPresent());
    }

    @Test
    public void testCreateAndUpdate() {
        GrowingLocationDTO growingLocationDTOForCreateAndUpdate = GrowingLocationDTO.builder()
                .name(GROWING_LOCATION_2_NAME)
                .occupied(false)
                .build();

        GrowingLocationDTO growingLocation = growingLocationService.createGrowingLocation(growingLocationDTOForCreateAndUpdate);

        GrowingLocationDTO updatedGrowingLocation = growingLocationService.updateGrowingLocation(growingLocation.id(), GrowingLocationDTO.builder()
                .name(GROWING_LOCATION_1_NAME_UPDATE)
                .occupied(false)
                .build());

        assertEquals(GROWING_LOCATION_1_NAME_UPDATE, updatedGrowingLocation.name());
    }

    @Test
    public void testCreateAndDelete() {
        // delete all data in order to verify that only one record is created and after delete non remains
        repositoryTestHelper.deleteAllData();

        GrowingLocationDTO growingLocationDTOForCreateAndUpdate = GrowingLocationDTO.builder()
                .name(GROWING_LOCATION_3_NAME)
                .occupied(false)
                .build();

        GrowingLocationDTO growingLocation = growingLocationService.createGrowingLocation(growingLocationDTOForCreateAndUpdate);
        assertEquals(1, growingLocationService.getAllGrowingLocations().size());

        growingLocationService.deleteGrowingLocation(growingLocation.id());
        assertEquals(0, growingLocationService.getAllGrowingLocations().size());
    }
}