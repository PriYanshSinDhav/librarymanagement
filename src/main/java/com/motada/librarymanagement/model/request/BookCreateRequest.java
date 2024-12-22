package com.motada.librarymanagement.model.request;


import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookCreateRequest {

    private String title;
    private String author;
    private String genre;
    private String publishedYear;

}
