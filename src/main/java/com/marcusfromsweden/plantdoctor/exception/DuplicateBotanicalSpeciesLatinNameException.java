package com.marcusfromsweden.plantdoctor.exception;

public class DuplicateBotanicalSpeciesLatinNameException extends BotanicalSpeciesException {

    public DuplicateBotanicalSpeciesLatinNameException(String latinName) {
        super("Botanical species with latin name %s already exists".formatted(latinName));
    }
}
