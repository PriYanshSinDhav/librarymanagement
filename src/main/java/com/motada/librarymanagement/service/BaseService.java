package com.motada.librarymanagement.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.sql.Timestamp;


@Service
@Slf4j
public class BaseService {



    public BaseService() {

    }



    protected final Timestamp currentTimestamp() {
        return new Timestamp(System.currentTimeMillis());
    }



    protected void logInfo(String... messages) {
        if (log.isInfoEnabled()) {
            StringBuilder concatenatedMessage = new StringBuilder();
            for (String message : messages) {
                concatenatedMessage.append(message).append(" ");
            }
            log.info(concatenatedMessage.toString().trim());
        }
    }

    protected void logDebug(String... messages) {
        if (log.isDebugEnabled()) {
            StringBuilder concatenatedMessage = new StringBuilder();
            for (String message : messages) {
                concatenatedMessage.append(message).append(" ");
            }
            log.info(concatenatedMessage.toString().trim());
        }
    }



    protected void logError(Exception e) {
        log.error(e.getMessage(),e);
    }

    protected void logError(Exception e, String... messages) {
        StringBuilder concatenatedMessage = new StringBuilder();
        for (String message : messages) {
            concatenatedMessage.append(message).append(" ");
        }
        concatenatedMessage.append(" ").append(e.getMessage());
        log.error(concatenatedMessage.toString(),e);
    }

    protected void logError(String... messages) {
        StringBuilder concatenatedMessage = new StringBuilder();
        for (String message : messages) {
            concatenatedMessage.append(message).append(" ");
        }
        log.error(concatenatedMessage.toString());
    }
}
