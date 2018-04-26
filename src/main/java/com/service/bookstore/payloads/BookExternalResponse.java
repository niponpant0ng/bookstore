package com.service.bookstore.payloads;

import com.fasterxml.jackson.annotation.JsonSetter;

public class BookExternalResponse {
    private Long id;
    private String name;
    private String author;
    private Double price;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }

    @JsonSetter("book_name")
    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    @JsonSetter("author_name")
    public void setAuthor(String author) {
        this.author = author;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
