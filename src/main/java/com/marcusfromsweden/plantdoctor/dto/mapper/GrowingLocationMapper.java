package com.marcusfromsweden.plantdoctor.dto.mapper;

import com.marcusfromsweden.plantdoctor.dto.GrowingLocationDTO;
import com.marcusfromsweden.plantdoctor.entity.GrowingLocation;
import org.springframework.stereotype.Component;

@Component
public class GrowingLocationMapper {

    public GrowingLocationDTO toDTO(GrowingLocation growingLocation) {
        return new GrowingLocationDTO(
                growingLocation.getId(),
                growingLocation.getName()
        );
    }

    public void updateEntityUsingDTO(GrowingLocation botanicalSpecies,
                                     GrowingLocationDTO growingLocationDTO) {
        toEntity(botanicalSpecies, growingLocationDTO);
    }

    public GrowingLocation toEntity(GrowingLocationDTO growingLocationDTO) {
        GrowingLocation growingLocation = new GrowingLocation();
        growingLocation.setId(growingLocationDTO.id());
        return toEntity(growingLocation, growingLocationDTO);
    }

    private GrowingLocation toEntity(GrowingLocation growingLocation,
                                     GrowingLocationDTO growingLocationDTO) {
        growingLocation.setName(growingLocationDTO.name());
        return growingLocation;
    }
}