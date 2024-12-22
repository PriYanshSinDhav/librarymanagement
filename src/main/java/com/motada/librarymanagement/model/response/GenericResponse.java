package com.motada.librarymanagement.model.response;

import lombok.Data;

@Data
public class GenericResponse<T> {

    private Long responseCode;
    private String responseMessage;
    private T response;

    public GenericResponse() {
    }

    public GenericResponse(Long var1, String var2) {
        this.responseCode = var1;
        this.responseMessage = var2;
    }

    public GenericResponse(Long var1, String var2, T var3) {
        this.responseCode = var1;
        this.responseMessage = var2;
        this.response = var3;
    }



    public String toString() {
        return "GenericResponse [responseCode=" + this.responseCode + ", responseMessage=" + this.responseMessage + ", response=" + this.response + "]";
    }
}
