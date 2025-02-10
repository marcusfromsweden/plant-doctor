package com.marcusfromsweden.plantdoctor;

import com.marcusfromsweden.plantdoctor.util.PostgresTestContainerTest;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PlantDoctorApplicationIT extends PostgresTestContainerTest {

    @Test
    void contextLoads() {
    }

}