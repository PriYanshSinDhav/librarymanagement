package com.motada.librarymanagement.model.request;

import lombok.Data;

@Data
public class MemberCreateRequest {

    private String email;
    private String name;
    private String phone;
}
