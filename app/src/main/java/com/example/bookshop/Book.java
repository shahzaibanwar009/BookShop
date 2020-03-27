package com.example.bookshop;

import javax.annotation.Nullable;

public class Book {
    @Nullable
    private String id;

    private String title;
    private String isbn;
    private String publish_date;

    public Book() {}

    public Book(String title, String isbn, String publish_date) {
        this.title = title;
        this.isbn = isbn;
        this.publish_date = publish_date;
    }

    @Nullable
    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getPublish_date() {
        return publish_date;
    }
}
