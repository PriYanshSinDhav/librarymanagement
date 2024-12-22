
package com.motada.librarymanagement.controller;


import com.motada.librarymanagement.entity.Book;
import com.motada.librarymanagement.model.SearchRequest;
import com.motada.librarymanagement.model.request.BookCreateRequest;
import com.motada.librarymanagement.model.response.BookListResponse;
import com.motada.librarymanagement.model.response.BookViewResponse;
import com.motada.librarymanagement.model.response.GenericResponse;
import com.motada.librarymanagement.service.BookService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @PostMapping
    public GenericResponse<String> addBook(@RequestBody BookCreateRequest book) {
        try{
            return bookService.addBook(book);
        }catch (Exception e){
            return new GenericResponse<>(-1L,e.getMessage());
        }

    }

    @PostMapping("/all")
    public GenericResponse<BookListResponse> getAllBooks(@RequestBody SearchRequest searchRequest) {
        return bookService.getAllBooks(searchRequest);
    }

    @GetMapping("/{id}")
    public GenericResponse<BookViewResponse> getBookById(@PathVariable Long id) {
        try{
            return bookService.getBookById(id);
        }catch (Exception e){
           return new GenericResponse<>(-1L,e.getMessage());
        }

    }

    @PutMapping("/{id}")
    public GenericResponse<String> updateBook(@PathVariable Long id, @RequestBody @Valid BookCreateRequest book) {
        try{
            return bookService.updateBook(id,book);
        }catch (Exception e){
            return new GenericResponse<>(-1L,e.getMessage());
        }

    }

    @DeleteMapping("/{id}")
    public GenericResponse<String> deleteBook(@PathVariable Long id) {
        try{
            return bookService.deleteBook(id);
        }catch (Exception e){
            return new GenericResponse<>(-1L,e.getMessage());
        }
    }
}
