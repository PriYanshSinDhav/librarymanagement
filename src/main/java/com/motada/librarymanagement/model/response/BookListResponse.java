package com.motada.librarymanagement.model.response;

import com.motada.librarymanagement.model.INPage;
import lombok.Data;

import java.util.List;

@Data
public class BookListResponse {


    private INPage page;
    private List<BookListItem> bookItemList ;
}
