package com.marcusfromsweden.plantdoctor.dto;

import java.time.LocalDateTime;

public record PlantCommentDTO(Long id,
                              Long plantId,
                              String text,
                              LocalDateTime createdDate) {
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Long id;
        private Long plantId;
        private String text;
        private LocalDateTime createdDate;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder plantId(Long plantId) {
            this.plantId = plantId;
            return this;
        }

        public Builder text(String text) {
            this.text = text;
            return this;
        }

        public Builder createdDate(LocalDateTime createdDate) {
            this.createdDate = createdDate;
            return this;
        }

        public PlantCommentDTO build() {
            return new PlantCommentDTO(id, plantId, text, createdDate);
        }
    }
}