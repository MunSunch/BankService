package com.munsun.dossier.services;

import com.munsun.dossier.queries.payload.EmailMessage;
import com.munsun.dossier.queries.payload.EmailMessageWithCreditDto;
import com.munsun.dossier.queries.payload.EmailMessageWithSesCode;
import org.springframework.mail.SimpleMailMessage;

public interface MessageFactory {
    SimpleMailMessage getMessage(EmailMessage theme);
    SimpleMailMessage getMessage(EmailMessageWithSesCode emailMessage);
    SimpleMailMessage getMessage(EmailMessageWithCreditDto emailMessage);
}
