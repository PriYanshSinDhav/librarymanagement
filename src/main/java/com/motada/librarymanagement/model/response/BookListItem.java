package com.motada.librarymanagement.model.response;

import lombok.Data;

@Data
public class BookListItem {


    private String author;
    private String genre;
    private String title;
    private String publishedYear;

    public BookListItem(String author, String genre, String title, String publishedYear) {
        this.author = author;
        this.genre = genre;
        this.title = title;
        this.publishedYear = publishedYear;
    }
}
