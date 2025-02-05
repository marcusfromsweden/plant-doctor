package com.marcusfromsweden.plantdoctor;

import com.marcusfromsweden.plantdoctor.util.CustomProperties;
import com.marcusfromsweden.plantdoctor.util.DataSourceProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({DataSourceProperties.class, CustomProperties.class})
public class PlantDoctorApplication {

    public static void main(String[] args) {
        SpringApplication.run(PlantDoctorApplication.class, args);
    }

}
