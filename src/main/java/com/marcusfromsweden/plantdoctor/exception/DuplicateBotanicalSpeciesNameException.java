package com.marcusfromsweden.plantdoctor.exception;

public class DuplicateBotanicalSpeciesNameException extends RuntimeException {
    public DuplicateBotanicalSpeciesNameException(String message) {
        super(message);
    }
}
