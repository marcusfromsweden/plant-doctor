package com.marcusfromsweden.plantdoctor.exception;

public class BotanicalSpeciesNotFoundByIdException extends BotanicalSpeciesException {

    public BotanicalSpeciesNotFoundByIdException(Long id) {
        super("BotanicalSpecies not found with id %d".formatted(id));
    }
}
