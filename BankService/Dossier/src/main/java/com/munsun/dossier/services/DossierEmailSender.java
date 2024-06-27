package com.munsun.dossier.services;

import javax.mail.internet.MimeMessage;

public interface DossierEmailSender {
    void send(String address, MimeMessage message);
}
