package com.motada.librarymanagement.model.response;

import lombok.Data;

import java.text.SimpleDateFormat;
import java.util.Date;

@Data
public class MemberListItem {

    private String name;
    private String email;
    private String phone;
    private String membershipDate;
    private String createdOn;


    public MemberListItem(String name, String email, String phone, Date membershipDate, Date createdOn) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.membershipDate = formatDateTime(membershipDate);
        this.createdOn = formatDateTime(createdOn);
    }



    public static String formatDateTime(Date date) {
        if (date != null) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat( "dd-MMM-yyyy HH:mm:ss" );
                return sdf.format(date);
            } catch (Throwable var2) {
            }
        }

        return "err";
    }

}
