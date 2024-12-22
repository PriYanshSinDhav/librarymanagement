package com.motada.librarymanagement.model.response;

import com.motada.librarymanagement.model.INPage;
import lombok.Data;

import java.util.List;

@Data
public class MemberListResponse {

    private INPage page;
    private List<MemberListItem> memberItemList;
}
