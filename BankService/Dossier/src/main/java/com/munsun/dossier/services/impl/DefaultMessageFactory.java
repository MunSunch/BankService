package com.munsun.dossier.services.impl;

import com.munsun.dossier.queries.payload.enums.Theme;
import com.munsun.dossier.services.MessageFactory;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;

@Service
public class DefaultMessageFactory implements MessageFactory {
    @Override
    public MimeMessage getMessage(Theme theme) {
        switch (theme) {
            case FINISH_REGISTRATION -> {
                return getFinishRegistrationMessage();
            }
            default -> {
                return getEmptyMessage();
            }
        }
    }

    private MimeMessage getEmptyMessage() {
        return new MimeMessage()
                .set
    }

    private MimeMessage getFinishRegistrationMessage() {
    }
}
