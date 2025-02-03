package com.marcusfromsweden.plantdoctor.util;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "com.marcusfromsweden")
public class CustomProperties {
    private boolean deleteAndPopulateTables;

    // Getters and setters
    public boolean isDeleteAndPopulateTables() {
        return deleteAndPopulateTables;
    }

    public void setDeleteAndPopulateTables(boolean deleteAndPopulateTables) {
        this.deleteAndPopulateTables = deleteAndPopulateTables;
    }
}