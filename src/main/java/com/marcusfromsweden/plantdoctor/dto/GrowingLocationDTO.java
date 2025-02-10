package com.marcusfromsweden.plantdoctor.dto;

public record GrowingLocationDTO(
        Long id,
        String name
) {
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Long id;
        private String name;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder name(String locationName) {
            this.name = locationName;
            return this;
        }

        public GrowingLocationDTO build() {
            return new GrowingLocationDTO(id, name);
        }
    }
}