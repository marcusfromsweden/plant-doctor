package com.marcusfromsweden.plantdoctor.exception;

public class PlantNotFoundByIdException extends PlantException {
    public PlantNotFoundByIdException(Long id) {
        super("Plant not found with id %d".formatted(id));
    }
}
