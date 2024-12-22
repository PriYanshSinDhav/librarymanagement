package com.motada.librarymanagement;


import com.motada.librarymanagement.entity.Book;
import com.motada.librarymanagement.entity.BookStatus;
import com.motada.librarymanagement.entity.BookTransaction;
import com.motada.librarymanagement.model.exceptions.CreateException;
import com.motada.librarymanagement.model.exceptions.SearchException;
import com.motada.librarymanagement.model.request.BookCreateRequest;
import com.motada.librarymanagement.model.request.BookTransactionRequest;
import com.motada.librarymanagement.model.request.MemberCreateRequest;
import com.motada.librarymanagement.model.response.GenericResponse;
import com.motada.librarymanagement.repo.BookTransactionRepository;
import com.motada.librarymanagement.service.BookService;
import com.motada.librarymanagement.service.BookTransactionService;
import com.motada.librarymanagement.service.MemberService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ActiveProfiles("test")
@SpringBootTest
public class BookTransactionControllerTest {

    @Autowired
    private BookService bookService;
    @Autowired
    private MemberService memberService;
    @Autowired
    private BookTransactionService bookTransactionService;

    @Autowired
    private BookTransactionRepository bookTransactionRepository;


    @Test
    public void testAddBookIntegration() throws CreateException, SearchException {




        BookCreateRequest bookCreateRequest = new BookCreateRequest();
        bookCreateRequest.setTitle("Integration Test Book");
        bookCreateRequest.setAuthor("Test Author");
        bookCreateRequest.setGenre("Test Genre");
        bookCreateRequest.setPublishedYear("2023");

        GenericResponse<String> genericResponse = bookService.addBook(bookCreateRequest);


        assertEquals(0L, genericResponse.getResponseCode());


        MemberCreateRequest memberCreateRequest = new MemberCreateRequest();
        memberCreateRequest.setEmail("priyansh@gmail.com");
        memberCreateRequest.setName("Priyansh");
        memberCreateRequest.setPhone("9225453243");

        GenericResponse<String> genericResponse1 = memberService.addMember(memberCreateRequest);


        assertEquals(0L, genericResponse1.getResponseCode());

        BookTransactionRequest bookTransactionRequest = new BookTransactionRequest();

        bookTransactionRequest.setBookId(Long.valueOf(genericResponse.getResponse()));
        bookTransactionRequest.setMemberId(Long.valueOf(genericResponse1.getResponse()));
        bookTransactionRequest.setStatus(BookStatus.BORROWED);

        GenericResponse<String> transactionResponse = bookTransactionService.addTransaction(bookTransactionRequest);

        assertEquals(0L, transactionResponse.getResponseCode());

        BookTransaction bookTransaction =  bookTransactionRepository.findById(Long.valueOf(transactionResponse.getResponse())).get();

        assertNotNull(bookTransaction.getId());
        assertEquals(genericResponse.getResponse(),String.valueOf(bookTransaction.getBookId()));
        assertEquals(genericResponse1.getResponse(),String.valueOf(bookTransaction.getMemberId()));

        assertEquals(bookTransactionRequest.getStatus(),bookTransaction.getStatus());


    }

}
