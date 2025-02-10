package com.marcusfromsweden.plantdoctor.exception;

public class GrowingLocationNotFoundByIdException extends GrowingLocationException {

    public GrowingLocationNotFoundByIdException(Long id) {
        super("Growing location not found with id %d".formatted(id));
    }
}
