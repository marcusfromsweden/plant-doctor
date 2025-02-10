package com.marcusfromsweden.plantdoctor.exception;

public class PlantDoctorException extends RuntimeException {
    public PlantDoctorException(String message) {
        super(message);
    }

    public PlantDoctorException(String message,
                                Throwable cause) {
        super(message, cause);
    }
}
