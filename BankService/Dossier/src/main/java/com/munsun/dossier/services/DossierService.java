package com.munsun.dossier.services;

import com.munsun.dossier.queries.payload.EmailMessage;
import com.munsun.dossier.queries.payload.EmailMessageWithCreditDto;
import com.munsun.dossier.queries.payload.EmailMessageWithSesCode;

import javax.mail.MessagingException;

public interface DossierService {
    void sendMessageEmail(EmailMessage emailMessage);
    void sendMessageEmail(EmailMessageWithCreditDto emailMessage) throws MessagingException;
    void sendMessageEmail(EmailMessageWithSesCode emailMessageWithSesCode);
}
