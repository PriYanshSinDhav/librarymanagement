
package com.motada.librarymanagement.service;

import com.motada.librarymanagement.entity.Book;
import com.motada.librarymanagement.entity.BookTransaction;
import com.motada.librarymanagement.entity.Member;
import com.motada.librarymanagement.model.INPage;
import com.motada.librarymanagement.model.SearchRequest;
import com.motada.librarymanagement.model.exceptions.CreateException;
import com.motada.librarymanagement.model.exceptions.SearchException;
import com.motada.librarymanagement.model.exceptions.UpdateException;
import com.motada.librarymanagement.model.request.BookTransactionRequest;
import com.motada.librarymanagement.model.response.*;
import com.motada.librarymanagement.repo.BookRepository;
import com.motada.librarymanagement.repo.BookTransactionRepository;
import com.motada.librarymanagement.repo.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class BookTransactionService extends BaseService{


    private final BookTransactionRepository transactionRepository;

    private final BookRepository bookRepository;

    private final MemberRepository memberRepository;

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
    public GenericResponse<TransactionListResponse> getAllTransactions(SearchRequest searchRequest) {

        Pageable pageable = PageRequest.of(searchRequest.getPageNumber(), searchRequest.getPageSize(), parseToValidSort(searchRequest.getSortColumn(), searchRequest.getSortDirection()));

        String name = "";
        if(searchRequest.getSearchFilter()!=null){
            name = searchRequest.getSearchFilter();;
        }

        Page<TransactionListItem> transactionListItemPage = transactionRepository.getTransactionsFilterByName(name,pageable);


        INPage page = new INPage(transactionListItemPage.getSize(), transactionListItemPage.getTotalElements(), transactionListItemPage.getTotalPages(),
                transactionListItemPage.getNumber());

        TransactionListResponse bookListResponse = new TransactionListResponse();
        bookListResponse.setMemberItemList(transactionListItemPage.getContent());
        bookListResponse.setPage(page);

        return new GenericResponse<>(0L , "Sucess" , bookListResponse);
    }

    @Transactional(rollbackFor = UpdateException.class)
    public BookTransaction updateTransaction(Long id, BookTransaction transaction) {
        transaction.setId(id);
        return transactionRepository.save(transaction);
    }





    protected Sort parseToValidSort(String columnName, String direction) {

        String sortColum;

        if (columnName != null) {
            switch (columnName) {
                case "book.title":
                    sortColum = columnName;
                    break;
                case "book.genre":
                    sortColum = columnName;
                    break;
                case "member.name":
                    sortColum = columnName;
                    break;
                case "publishedYear":
                    sortColum = columnName;
                    break;
                default:
                    sortColum = "transactionDate";
            }
        } else {
            sortColum = "transactionDate";
        }

        if (direction != null && direction.equals("asc")) {
            return Sort.by(sortColum).ascending();
        } else {
            return Sort.by(sortColum).descending();
        }

    }

}
