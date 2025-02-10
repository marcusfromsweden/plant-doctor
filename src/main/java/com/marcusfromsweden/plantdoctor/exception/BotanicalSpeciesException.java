package com.marcusfromsweden.plantdoctor.exception;

public class BotanicalSpeciesException extends PlantDoctorException {
    
    public BotanicalSpeciesException(String message) {
        super(message);
    }

    public BotanicalSpeciesException(String message,
                                     Throwable cause) {
        super(message, cause);
    }
}
