package com.marcusfromsweden.plantdoctor.repository;

import com.marcusfromsweden.plantdoctor.dto.mapper.GrowingLocationMapper;
import com.marcusfromsweden.plantdoctor.entity.GrowingLocation;
import com.marcusfromsweden.plantdoctor.util.GrowingLocationTestHelper;
import com.marcusfromsweden.plantdoctor.util.PostgresTestContainerTest;
import com.marcusfromsweden.plantdoctor.util.RepositoryTestHelper;
import com.marcusfromsweden.plantdoctor.util.SecurityTestUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import({GrowingLocationTestHelper.class, GrowingLocationMapper.class})
public class GrowingLocationRepository_UniqueNameConstraintIT extends PostgresTestContainerTest {

    private static final String GROWING_LOCATION_NAME_1 = "GROWING_LOCATION_NAME_1";
    private static final String GROWING_LOCATION_NAME_2 = "GROWING_LOCATION_NAME_2";
    private static final String GROWING_LOCATION_NAME_3 = "GROWING_LOCATION_NAME_3";

    @Autowired
    private GrowingLocationRepository growingLocationRepository;
    @Autowired
    private GrowingLocationTestHelper growingLocationTestHelper;
    @Autowired
    private RepositoryTestHelper repositoryTestHelper;

    @BeforeEach
    public void setupTest() {
        repositoryTestHelper.deleteAllData();
    }

    @Test
    void shouldNotAcceptDuplicateName() {
        GrowingLocation location =
                growingLocationTestHelper.createEntity(null,
                                                       GROWING_LOCATION_NAME_1);

        GrowingLocation locationWithSameName =
                growingLocationTestHelper.createEntity(null,
                                                       GROWING_LOCATION_NAME_1);

        growingLocationRepository.save(location);

        assertThrows(DataIntegrityViolationException.class, () ->
                growingLocationRepository.save(locationWithSameName)
        );
    }

    @Test
    void shouldAcceptNonUniqueName() {
        GrowingLocation location =
                growingLocationTestHelper.createEntity(null,
                                                       GROWING_LOCATION_NAME_2);

        GrowingLocation locationWithDifferentName =
                growingLocationTestHelper.createEntity(null,
                                                       GROWING_LOCATION_NAME_3);

        growingLocationRepository.save(location);

        assertDoesNotThrow(() -> {
            growingLocationRepository.save(locationWithDifferentName);
        });
    }
}
