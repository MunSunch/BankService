package com.munsun.dossier.services;

import com.munsun.dossier.queries.payload.enums.Theme;

import javax.mail.internet.MimeMessage;

public interface MessageFactory {
    MimeMessage getMessage(Theme theme);
}
