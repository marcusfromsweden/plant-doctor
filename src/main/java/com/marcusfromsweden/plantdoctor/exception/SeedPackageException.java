package com.marcusfromsweden.plantdoctor.exception;

public class SeedPackageException extends PlantDoctorException {
    public SeedPackageException(String message) {
        super(message);
    }

    public SeedPackageException(String message,
                                Throwable cause) {
        super(message, cause);
    }
}
