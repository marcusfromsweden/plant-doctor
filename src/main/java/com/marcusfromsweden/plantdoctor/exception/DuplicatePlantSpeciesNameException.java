package com.marcusfromsweden.plantdoctor.exception;

public class DuplicatePlantSpeciesNameException extends RuntimeException {
    public DuplicatePlantSpeciesNameException(String message) {
        super(message);
    }
}
