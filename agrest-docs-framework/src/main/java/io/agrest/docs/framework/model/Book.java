package io.agrest.docs.framework.model;

import io.agrest.annotation.AgAttribute;
import io.agrest.annotation.AgId;
import io.agrest.annotation.AgRelationship;

// tag::all[]
public class Book {

    private long id;
    private String title;
    private String publishStatus;
    private Author author;

    @AgId // <1>
    public long getId() {
        return id;
    }

    @AgAttribute(writable = false) // <2>
    public String getTitle() {
        return title;
    }

    @AgRelationship // <3>
    public Author getAuthor() {
        return author;
    }

    public Book setId(long id) {
        this.id = id;
        return this;
    }

    public Book setTitle(String title) {
        this.title = title;
        return this;
    }

    public Book setAuthor(Author author) {
        this.author = author;
        return this;
    }

    public Book setPublishStatus(String publishStatus) {
        this.publishStatus = publishStatus;
        return this;
    }
}
// end::all[]