package com.marcusfromsweden.plantdoctor.service;

import com.marcusfromsweden.plantdoctor.dto.PlantDTO;
import com.marcusfromsweden.plantdoctor.dto.QuickCreatePlantDTO;
import com.marcusfromsweden.plantdoctor.repository.PlantRepository;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PlantDoctorService_TestQuickCreateIT {

    private final Logger log = LoggerFactory.getLogger(PlantDoctorService_TestQuickCreateIT.class);

    @Autowired
    private PlantDoctorService plantDoctorService;

    @Autowired
    private PlantRepository plantRepository;

    @BeforeEach
    void setUp() {
        //plantRepository.deleteAll();
    }

    @Test
    public void testQuickCreatePlant() {
        QuickCreatePlantDTO quickCreatePlantDTO = new QuickCreatePlantDTO(
                LocalDate.now(),
                "Test Species",
                "Test Location",
                "This is a test comment");

        // use plantRepository.count() and log the count
        log.info("1. Plant count: {}", plantRepository.count());

        PlantDTO createdPlant = plantDoctorService.quickCreatePlant(quickCreatePlantDTO);

        log.info("2. Plant count: {}", plantRepository.count());

        assertNotNull(createdPlant);
        assertNotNull(createdPlant.id());
        assertNotNull(plantRepository.findById(createdPlant.id()).orElse(null));
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