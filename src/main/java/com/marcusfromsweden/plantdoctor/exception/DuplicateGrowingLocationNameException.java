package com.marcusfromsweden.plantdoctor.exception;

public class DuplicateGrowingLocationNameException extends RuntimeException {
    public DuplicateGrowingLocationNameException(String message) {
        super(message);
    }
}
