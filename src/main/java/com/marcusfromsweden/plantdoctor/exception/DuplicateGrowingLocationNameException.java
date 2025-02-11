package com.marcusfromsweden.plantdoctor.exception;

public class DuplicateGrowingLocationNameException extends GrowingLocationException {
    public DuplicateGrowingLocationNameException(String growingLocationName) {
        super("Growing location with name %s already exists.".formatted(growingLocationName));
    }
}
