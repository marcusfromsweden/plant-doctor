package com.marcusfromsweden.plantdoctor.exception;

public class SeedPackageNotFoundByIdException extends SeedPackageException {
    public SeedPackageNotFoundByIdException(Long id) {
        super("Seed package not found with id %d".formatted(id));
    }
}
