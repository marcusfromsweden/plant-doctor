package com.marcusfromsweden.plantdoctor.dto;

public record BotanicalSpeciesDTO(
        Long id,
        String name,
        String description,
        Integer estimatedDaysToGermination
) {
    public static BotanicalSpeciesDTO.Builder builder() {
        return new BotanicalSpeciesDTO.Builder();
    }

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

        public BotanicalSpeciesDTO build() {
            return new BotanicalSpeciesDTO(id, name, description, estimatedDaysToGermination);
        }
    }
}