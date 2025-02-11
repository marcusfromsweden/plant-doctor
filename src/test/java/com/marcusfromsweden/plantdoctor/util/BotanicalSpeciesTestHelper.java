package com.marcusfromsweden.plantdoctor.util;

import com.marcusfromsweden.plantdoctor.dto.BotanicalSpeciesDTO;
import org.springframework.stereotype.Component;

@Component
public class BotanicalSpeciesTestHelper {
    public BotanicalSpeciesDTO createDTO(Long id,
                                         String latinName,
                                         String description,
                                         int daysToGerminate) {
        return BotanicalSpeciesDTO.builder()
                .id(id)
                .latinName(latinName)
                .description(description)
                .estimatedDaysToGermination(daysToGerminate)
                .build();
    }
}
