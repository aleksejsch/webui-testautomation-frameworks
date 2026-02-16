package de.aleksejsch.selenium.dto;

import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public record Owner(
        String firstName,
        String lastName,
        String address,
        String city,
        String telephone,
        int id
) {

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String firstName = "First" + getRandomString(20);
        private String lastName = "Last" + getRandomString(20);
        private String address = ThreadLocalRandom.current().nextInt(100, 999) + " " + getRandomString(20);
        private String city = "City"+ getRandomString(20);
        private String telephone = String.valueOf(ThreadLocalRandom.current().nextLong(1000000000L, 9999999999L));

        public Builder firstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public Builder lastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public Builder address(String address) {
            this.address = address;
            return this;
        }

        public Builder city(String city) {
            this.city = city;
            return this;
        }

        public Builder telephone(String telephone) {
            this.telephone = telephone;
            return this;
        }

        public Owner build() {
            return new Owner(firstName, lastName, address, city, telephone, -1);
        }

        private String getRandomString(int size){
            return  UUID.randomUUID().toString().substring(0, size).replace("-", "");
        }
    }


}





