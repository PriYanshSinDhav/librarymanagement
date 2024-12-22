
package com.motada.librarymanagement.service;

import com.motada.librarymanagement.entity.Book;
import com.motada.librarymanagement.entity.BookTransaction;
import com.motada.librarymanagement.entity.Member;
import com.motada.librarymanagement.model.exceptions.CreateException;
import com.motada.librarymanagement.model.exceptions.SearchException;
import com.motada.librarymanagement.model.exceptions.UpdateException;
import com.motada.librarymanagement.model.request.BookTransactionRequest;
import com.motada.librarymanagement.model.response.GenericResponse;
import com.motada.librarymanagement.repo.BookRepository;
import com.motada.librarymanagement.repo.BookTransactionRepository;
import com.motada.librarymanagement.repo.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BookTransactionService extends BaseService{

    @Autowired
    private BookTransactionRepository transactionRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Transactional(rollbackFor = CreateException.class)
    public GenericResponse<String> addTransaction(BookTransactionRequest bookTransactionRequest) throws SearchException, CreateException {

        Book book = bookRepository.findById(bookTransactionRequest.getBookId()).orElseThrow(()-> new SearchException("Book not found"));

        Member member = memberRepository.findById(bookTransactionRequest.getMemberId()).orElseThrow(()-> new SearchException("Member not found"));

        try{
            BookTransaction bookTransaction = new BookTransaction();
            bookTransaction.setBook(book);
            bookTransaction.setMember(member);
            bookTransaction.setStatus(bookTransactionRequest.getStatus());
            bookTransaction.setTransactionDate(currentTimestamp());
            bookTransaction.setCreatedOn(currentTimestamp());
            bookTransaction = transactionRepository.save(bookTransaction);

            return new GenericResponse<>(0L, "Success",String.valueOf(bookTransaction.getId()));
        }catch (Exception e){
            logError(e);
            throw new CreateException("Error creating book transaction reaosn :" + e.getMessage());
        }

    }

    @Transactional(readOnly = true)
    public List<BookTransaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    @Transactional(rollbackFor = UpdateException.class)
    public BookTransaction updateTransaction(Long id, BookTransaction transaction) {
        transaction.setId(id);
        return transactionRepository.save(transaction);
    }
}
