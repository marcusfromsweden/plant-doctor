package com.marcusfromsweden.plantdoctor.exception;

public class GrowingLocationException extends PlantDoctorException {
    public GrowingLocationException(String message) {
        super(message);
    }

    public GrowingLocationException(String message,
                                    Throwable cause) {
        super(message, cause);
    }
}
