
package com.motada.librarymanagement.controller;

import com.motada.librarymanagement.entity.BookTransaction;
import com.motada.librarymanagement.model.request.BookTransactionRequest;
import com.motada.librarymanagement.model.response.GenericResponse;
import com.motada.librarymanagement.service.BookTransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
public class BookTransactionController {

    @Autowired
    private BookTransactionService transactionService;

    @PostMapping
    public GenericResponse<String> addTransaction(@RequestBody BookTransactionRequest transaction) {
        try{
            return transactionService.addTransaction(transaction);
        }catch (Exception e){
            return new GenericResponse<>(-1L,e.getMessage());
        }

    }

    @GetMapping
    public List<BookTransaction> getAllTransactions() {
        return transactionService.getAllTransactions();
    }

    @PutMapping("/{id}")
    public BookTransaction updateTransaction(@PathVariable Long id, @RequestBody BookTransaction transaction) {
        return transactionService.updateTransaction(id, transaction);
    }
}
