package com.marcusfromsweden.plantdoctor.exception;

public class PlantCommentException extends PlantDoctorException {
    public PlantCommentException(String message) {
        super(message);
    }

    public PlantCommentException(String message,
                                 Throwable cause) {
        super(message, cause);
    }
}
