package com.marcusfromsweden.plantdoctor.exception;

public class DuplicateBotanicalSpeciesNameException extends BotanicalSpeciesException {
    public DuplicateBotanicalSpeciesNameException(String botanicalSpeciesName) {
        super("Duplicate botanical species name: " + botanicalSpeciesName);
    }
}
