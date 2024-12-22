package com.motada.librarymanagement.model;

import lombok.Data;


@Data
public class SearchRequest {

    private String searchFilter;
    private int pageNumber;
    private int pageSize;
    private String sortColumn;
    private String sortDirection;

}
