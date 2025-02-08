package com.marcusfromsweden.plantdoctor.exception;

public class DuplicateGrowingLocationNameException extends GrowingLocationException {
    public DuplicateGrowingLocationNameException(String message) {
        super(message);
    }
}
