
package com.motada.librarymanagement.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;

@Entity
@Data
@Table(name = "BOOKS")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "TITLE")
    private String title;
    @Column(name = "AUTHOR")
    private String author;
    @Column(name = "GENRE")
    private String genre;
    @Column(name = "PUBLISHEDYEAR")
    private String publishedYear;
    @Column(name = "AVAILABILITY")
    private boolean availability ;
    @Column(name = "DELETED")
    private boolean deleted ;
    @Column(name = "CREATEDON")
    private Timestamp createdOn;
    @Column(name = "UPDATEDON")
    private Timestamp updatedOn;


}
