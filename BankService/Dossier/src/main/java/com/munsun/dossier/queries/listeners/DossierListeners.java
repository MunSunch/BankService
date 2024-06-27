package com.munsun.dossier.queries.listeners;

import com.munsun.dossier.services.MessageFactory;
import com.munsun.dossier.queries.payload.EmailMessage;
import com.munsun.dossier.services.DossierEmailSender;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;

@Service
@RequiredArgsConstructor
public class DossierListeners {
    private final DossierEmailSender emailSender;
    private final MessageFactory messageFactory;

    @KafkaListener(groupId = "${spring.kafka.consumer.group-id}",
                   topics = "new String() {" +
                           "${kafka.topics.finish_registration}," +
                           "${kafka.topics.create_documents}," +
                           "${kafka.topics.send_documents}," +
                           "${kafka.topics.send_ses}," +
                           "${kafka.topics.credit_issued}}")
    public void handleFinishRegistration(EmailMessage emailMessage) {
        MimeMessage message = messageFactory.getMessage(emailMessage.theme());
        emailSender.send(emailMessage.address(), message);
    }
}
