package de.aleksejsch.selenium.dto;

import java.time.LocalDate;
import java.util.UUID;

public record Pet(
        String name,
        LocalDate birthdate,
        String type,
        int id
) {

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String name = "Pet" + getRandomString(20);
        private LocalDate birthdate = LocalDate.now().minusDays(1);
        private String type = "cat";
        private int id = -1;

        public Builder birthdate(int year, int month, int dayOfMonth) {
            this.birthdate = LocalDate.of(year, month, dayOfMonth);
            return this;
        }

        public Builder birthdate(LocalDate birthdate) {
            this.birthdate = birthdate;
            return this;
        }

        public Builder type(String type) {
            this.type = type;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder id(int id) {
            this.id = id;
            return this;
        }

        public Pet build() {
            return new Pet(name, birthdate, type, id);
        }

        private String getRandomString(int size){
            return  UUID.randomUUID().toString().substring(0, size).replace("-", "");
        }
    }


}





