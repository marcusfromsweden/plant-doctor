package com.marcusfromsweden.plantdoctor.exception;

public class PlantException extends PlantDoctorException {
    public PlantException(String message) {
        super(message);
    }

    public PlantException(String message,
                          Throwable cause) {
        super(message, cause);
    }
}
