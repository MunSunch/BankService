package com.munsun.dossier.services.impl;

import com.munsun.dossier.services.DossierEmailSender;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
@RequiredArgsConstructor
public class DefaultDossierEmailSender implements DossierEmailSender {
    private final JavaMailSender sender;
    private final ExecutorService pool =
            Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    @Override
    public void send(SimpleMailMessage message) {
        pool.submit(() -> {
            sender.send(message);
        });
    }
}
