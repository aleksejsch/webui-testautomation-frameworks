# Spring Boot PetClinic 3.4.0 tests written with Selenium Java test framework

## Requirements
- Java 21

## Tests execution

```bash

mvn clean test -Dtest.env=local -Dbrowser.headless=true -Dtest.browser=chrome