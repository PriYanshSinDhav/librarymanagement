package com.motada.librarymanagement.model.request;


import com.motada.librarymanagement.entity.BookStatus;
import lombok.Data;

@Data
public class BookTransactionRequest {
    private Long bookId;
    private Long memberId;
    private BookStatus status;
}
