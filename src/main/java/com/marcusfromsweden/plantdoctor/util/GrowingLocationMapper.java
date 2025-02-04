package com.marcusfromsweden.plantdoctor.util;

import com.marcusfromsweden.plantdoctor.dto.GrowingLocationDTO;
import com.marcusfromsweden.plantdoctor.entity.GrowingLocation;

public class GrowingLocationMapper {

    public static GrowingLocationDTO toDTO(GrowingLocation growingLocation) {
        return new GrowingLocationDTO(
                growingLocation.getId(),
                growingLocation.getName(),
                growingLocation.isOccupied()
        );
    }

    public static GrowingLocation toEntity(GrowingLocationDTO growingLocationDTO) {
        GrowingLocation growingLocation = new GrowingLocation();
        growingLocation.setId(growingLocationDTO.id());
        growingLocation.setName(growingLocationDTO.name());
        growingLocation.setOccupied(growingLocationDTO.occupied());
        return growingLocation;
    }

}