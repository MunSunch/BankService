package com.munsun.dossier.services.impl;

import com.munsun.dossier.services.DossierEmailSender;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;

@Service
@RequiredArgsConstructor
public class DefaultDossierEmailSender implements DossierEmailSender {
    private final JavaMailSender sender;

    @Override
    public void send(String address, MimeMessage message) {

    }
}
