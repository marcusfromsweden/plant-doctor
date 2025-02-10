package com.marcusfromsweden.plantdoctor.dto;

public record SeedPackageDTO(
        Long id,
        Long botanicalSpeciesId,
        String name,
        Integer numberOfSeeds
) {
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Long id;
        private Long botanicalSpeciesId;
        private String name;
        private Integer numberOfSeeds;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder botanicalSpeciesId(Long botanicalSpeciesId) {
            this.botanicalSpeciesId = botanicalSpeciesId;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder numberOfSeeds(Integer numberOfSeeds) {
            this.numberOfSeeds = numberOfSeeds;
            return this;
        }

        public SeedPackageDTO build() {
            return new SeedPackageDTO(id, botanicalSpeciesId, name, numberOfSeeds);
        }
    }
}