package io.agrest.tutorial.sb.persistence;

import io.agrest.annotation.AgAttribute;
import io.agrest.tutorial.sb.persistence.auto._Author;

import java.time.LocalDate;
import java.time.Period;

public class Author extends _Author {

    // tag::age[]
    @AgAttribute
    public int getAge() {
        return Period.between(getDateOfBirth(), LocalDate.now()).getYears();
    }
    // end::age[]
}
