package com.marcusfromsweden.plantdoctor.dto;

public record PlantSpeciesDTO(
        Long id,
        String name,
        String description,
        Integer estimatedDaysToGermination
) {
    public static class Builder {
        private Long id;
        private String name;
        private String description;
        private Integer estimatedDaysToGermination;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public Builder estimatedDaysToGermination(Integer estimatedDaysToGermination) {
            this.estimatedDaysToGermination = estimatedDaysToGermination;
            return this;
        }

        public PlantSpeciesDTO build() {
            return new PlantSpeciesDTO(id, name, description, estimatedDaysToGermination);
        }
    }
}