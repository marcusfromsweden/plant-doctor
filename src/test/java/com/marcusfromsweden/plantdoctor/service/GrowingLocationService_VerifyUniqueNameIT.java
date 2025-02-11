package com.marcusfromsweden.plantdoctor.service;

import com.marcusfromsweden.plantdoctor.entity.GrowingLocation;
import com.marcusfromsweden.plantdoctor.repository.GrowingLocationRepository;
import com.marcusfromsweden.plantdoctor.util.GrowingLocationTestHelper;
import com.marcusfromsweden.plantdoctor.util.PostgresTestContainerTest;
import com.marcusfromsweden.plantdoctor.util.RepositoryTestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.dao.DataIntegrityViolationException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import(GrowingLocationTestHelper.class)
public class GrowingLocationService_VerifyUniqueNameIT extends PostgresTestContainerTest {

    public static final String GROWING_LOCATION_NAME_1 = "GROWING_LOCATION_NAME_1";
    public static final String GROWING_LOCATION_NAME_2 = "GROWING_LOCATION_NAME_2";
    public static final String GROWING_LOCATION_NAME_3 = "GROWING_LOCATION_NAME_3";

    @Autowired
    private GrowingLocationRepository growingLocationRepository;
    @Autowired
    private GrowingLocationTestHelper growingLocationTestHelper;
    @Autowired
    private RepositoryTestHelper repositoryTestHelper;

    @BeforeEach
    public void testSetup() {
        repositoryTestHelper.deleteAllData();
    }

    @Test
    void shouldNotAcceptDuplicateName() {
        GrowingLocation location1 = new GrowingLocation();
        location1.setName(GROWING_LOCATION_NAME_1);

        //todo Use GrowingLocationTestHelper.createEntity(null, GROWING_LOCATION_NAME_1);

        GrowingLocation location2 = new GrowingLocation();
        location2.setName(GROWING_LOCATION_NAME_1);

        growingLocationRepository.save(location1);

        assertThrows(DataIntegrityViolationException.class, () ->
                growingLocationRepository.save(location2)
        );
    }

    @Test
    void shouldAcceptNonUniqueName() {
        GrowingLocation location1 = new GrowingLocation();
        location1.setName(GROWING_LOCATION_NAME_2);

        GrowingLocation location2 = new GrowingLocation();
        location2.setName(GROWING_LOCATION_NAME_3);

        growingLocationRepository.save(location1);

        assertDoesNotThrow(() -> {
            growingLocationRepository.save(location2);
        });
    }
}
