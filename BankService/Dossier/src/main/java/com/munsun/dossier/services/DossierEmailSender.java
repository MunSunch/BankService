package com.munsun.dossier.services;

import org.springframework.mail.SimpleMailMessage;

public interface DossierEmailSender {
    void send(SimpleMailMessage message);
}
