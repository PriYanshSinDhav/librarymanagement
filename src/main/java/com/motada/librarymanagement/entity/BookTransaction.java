
package com.motada.librarymanagement.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;

@Entity
@Data
@Table(name = "BOOK_TRANSACTIONS")
public class BookTransaction{


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBERID")
    private Member member;

    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinColumn(name = "BOOKID")
    private Book book;


    @Column(name = "MEMBERID",insertable = false,updatable = false)
    private Long memberId;

    @Column(name = "BOOKID",insertable = false, updatable = false)
    private Long bookId;

    @Column(name = "TRANSACTIONDATE")
    private Timestamp transactionDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS")
    private BookStatus status ;

    @Column(name = "CREATEDON")
    private Timestamp createdOn;


}
