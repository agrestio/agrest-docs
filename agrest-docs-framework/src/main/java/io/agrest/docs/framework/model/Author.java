package io.agrest.docs.framework.model;

import java.time.LocalDate;
import java.util.List;

public class Author {

    private LocalDate dateOfBirth;
    private String name;
    private List<Book> books;

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public String getName() {
        return name;
    }

    public List<Book> getBooks() {
        return books;
    }
}
