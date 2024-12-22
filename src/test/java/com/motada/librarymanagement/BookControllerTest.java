package com.motada.librarymanagement;


import com.motada.librarymanagement.entity.Book;
import com.motada.librarymanagement.model.exceptions.CreateException;
import com.motada.librarymanagement.model.exceptions.DeleteException;
import com.motada.librarymanagement.model.exceptions.SearchException;
import com.motada.librarymanagement.model.request.BookCreateRequest;
import com.motada.librarymanagement.model.response.BookViewResponse;
import com.motada.librarymanagement.model.response.GenericResponse;
import com.motada.librarymanagement.repo.BookRepository;
import com.motada.librarymanagement.service.BookService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

//@DataJpaTest
@ActiveProfiles("test")
@SpringBootTest
public class BookControllerTest {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private BookService bookService;

    @Test
    public void testAddBookIntegration() throws CreateException {




        BookCreateRequest bookCreateRequest = new BookCreateRequest();
        bookCreateRequest.setTitle("Integration Test Book");
        bookCreateRequest.setAuthor("Test Author");
        bookCreateRequest.setGenre("Test Genre");
        bookCreateRequest.setPublishedYear("2023");

        GenericResponse<String> genericResponse = bookService.addBook(bookCreateRequest);


        assertEquals(0L, genericResponse.getResponseCode());
        Book book = bookRepository.findById(Long.valueOf(genericResponse.getResponse())).get();

        assertNotNull(book.getId());
        assertEquals("Integration Test Book", book.getTitle());

        assertNotNull(book.getId(), "Book ID should not be null after saving");
        assertEquals("Integration Test Book", book.getTitle());
        assertEquals("Test Author", book.getAuthor());
        assertEquals("Test Genre", book.getGenre());
        assertEquals("2023", book.getPublishedYear());
        assertTrue(book.isAvailability());
        assertFalse(book.isDeleted() );
        assertNotNull(book.getCreatedOn(), "Book createdOn timestamp should not be null");
        assertNotNull(book.getUpdatedOn(), "Book updatedOn timestamp should not be null");
    }


    @Test
    public void testGetBookByIdIntegration() throws CreateException, SearchException {
        BookCreateRequest bookCreateRequest = new BookCreateRequest();
        bookCreateRequest.setTitle("Integration Test Book");
        bookCreateRequest.setAuthor("Test Author");
        bookCreateRequest.setGenre("Test Genre");
        bookCreateRequest.setPublishedYear("2023");

        GenericResponse<String> genericResponse = bookService.addBook(bookCreateRequest);


        assertEquals(0L, genericResponse.getResponseCode());


        GenericResponse<BookViewResponse> genericResponse1 =  bookService.getBookById(Long.valueOf(genericResponse.getResponse()));
        assertEquals(0L, genericResponse1.getResponseCode());
        BookViewResponse bookViewResponse = genericResponse1.getResponse();

        assertEquals("Integration Test Book", bookViewResponse.getTitle());
        assertEquals("Integration Test Book", bookViewResponse.getTitle());
        assertEquals("Test Author", bookViewResponse.getAuthor());
        assertEquals("Test Genre", bookViewResponse.getGenre());
        assertEquals("2023", bookViewResponse.getPublishedYear());
        assertEquals("Yes", bookViewResponse.getAvailable());

    }


    @Test
    public void testDeleteBook() throws CreateException, SearchException, DeleteException {
        BookCreateRequest bookCreateRequest = new BookCreateRequest();
        bookCreateRequest.setTitle("Integration Test Book");
        bookCreateRequest.setAuthor("Test Author");
        bookCreateRequest.setGenre("Test Genre");
        bookCreateRequest.setPublishedYear("2023");

        GenericResponse<String> genericResponse = bookService.addBook(bookCreateRequest);


        assertEquals(0L, genericResponse.getResponseCode());


        GenericResponse<String> genericResponse1 =  bookService.deleteBook(Long.valueOf(genericResponse.getResponse()));
        assertEquals(0L, genericResponse1.getResponseCode());
        Optional<Book> foundBook = bookRepository.findById(Long.valueOf(genericResponse1.getResponse()));

        assertTrue(foundBook.isPresent());
        assertTrue(foundBook.get().isDeleted());
    }


}
