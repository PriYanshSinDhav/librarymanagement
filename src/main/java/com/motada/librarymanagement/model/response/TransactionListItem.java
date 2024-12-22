package com.motada.librarymanagement.model.response;

import lombok.Data;

import java.text.SimpleDateFormat;
import java.util.Date;

@Data
public class TransactionListItem {

    private String memberName;
    private String mobile;
    private String bookName;
    private String transactionDate;


    public TransactionListItem(String memberName, String mobile, String bookName, Date transactionDate) {
        this.memberName = memberName;
        this.mobile = mobile;
        this.bookName = bookName;
        this.transactionDate = formatDateTime(transactionDate);
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
