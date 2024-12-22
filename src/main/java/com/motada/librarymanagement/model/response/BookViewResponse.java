package com.motada.librarymanagement.model.response;

import lombok.Data;

@Data
public class BookViewResponse {
    private String title;
    private String author;
    private String genre;
    private String publishedYear;
    private String available;
}
