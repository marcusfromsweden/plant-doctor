package com.marcusfromsweden.plantdoctor.service;

import com.marcusfromsweden.plantdoctor.entity.GrowingLocation;
import com.marcusfromsweden.plantdoctor.repository.GrowingLocationRepository;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class GrowingLocationService_VerifyUniqueNameIT {
    public static final String GROWING_LOCATION_NAME_1 = "GROWING_LOCATION_NAME_1";
    public static final String GROWING_LOCATION_NAME_2 = "GROWING_LOCATION_NAME_2";
    public static final String GROWING_LOCATION_NAME_3 = "GROWING_LOCATION_NAME_3";

    @Autowired
    private GrowingLocationRepository growingLocationRepository;

    @Test
    void testUniqueNameConstraint() {
        GrowingLocation location1 = new GrowingLocation();
        location1.setName(GROWING_LOCATION_NAME_1);

        GrowingLocation location2 = new GrowingLocation();
        location2.setName(GROWING_LOCATION_NAME_1);

        growingLocationRepository.save(location1);

        assertThrows(DataIntegrityViolationException.class, () ->
                growingLocationRepository.save(location2)
        );
    }

    @Test
    void testNonUniqueNameConstraint() {
        GrowingLocation location1 = new GrowingLocation();
        location1.setName(GROWING_LOCATION_NAME_2);

        GrowingLocation location2 = new GrowingLocation();
        location2.setName(GROWING_LOCATION_NAME_3);

        growingLocationRepository.save(location1);

        assertDoesNotThrow(() -> {
            growingLocationRepository.save(location2);
        });
    }

    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16-alpine");

    @BeforeAll
    static void beforeAll() {
        postgres.start();
    }

    @AfterAll
    static void afterAll() {
        postgres.stop();
    }

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }
}
