package de.aleksejsch.selenium.dto;

import java.time.LocalDate;
import java.util.UUID;

public record Visit(
        String description,
        LocalDate date
) {

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String description = "Visit" + getRandomString(20);
        private LocalDate date = LocalDate.now();

        public Builder date(int year, int month, int dayOfMonth) {
            this.date = LocalDate.of(year, month, dayOfMonth);
            return this;
        }

        public Builder date(LocalDate date) {
            this.date = date;
            return this;
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public Visit build() {
            return new Visit(description, date);
        }

        private String getRandomString(int size){
            return  UUID.randomUUID().toString().substring(0, size).replace("-", "");
        }
    }


}





