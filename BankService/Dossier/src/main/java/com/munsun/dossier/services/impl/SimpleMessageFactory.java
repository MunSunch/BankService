package com.munsun.dossier.services.impl;

import com.munsun.dossier.queries.payload.EmailMessage;
import com.munsun.dossier.queries.payload.EmailMessageWithCreditDto;
import com.munsun.dossier.queries.payload.EmailMessageWithSesCode;
import com.munsun.dossier.services.MessageFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class SimpleMessageFactory implements MessageFactory {
    @Override
    public SimpleMailMessage getMessage(EmailMessage emailMessage) {
        SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(emailMessage.address());
            message.setSubject("Кредитные уведомления");
        String text = "";
        switch (emailMessage.theme()) {
            case FINISH_REGISTRATION -> {
                text = "Завершите регистрацию для получения кредита";
            }
            case PREPARE_DOCUMENTS -> {
                text = "Документы формируются, ожидайте";
            }
            case CC_DENIED -> {
                text = "В кредите отказано, заполните заявку позже";
            }
            case CC_APPROVED -> {
                text = "Кредит одобрен";
            }
            case CREATED_DOCUMENTS -> {
                text = "Можете запросить документы для ознакомления с кредитным предложением";
            }
            case SIGN_DOCUMENTS -> {
                text = "Поздравляем! Документы подписаны, можете пользоваться кредитом";
            }
        }
        message.setText(text);
        return message;
    }

    @Override
    public SimpleMailMessage getMessage(EmailMessageWithSesCode emailMessage) {
        SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(emailMessage.address());
            message.setSubject("Кредитные уведомления");
            message.setText(String.format("Код подтверждения операции: %s", emailMessage.sesCodeConfirm().toString()));
        return message;
    }

    @Override
    public SimpleMailMessage getMessage(EmailMessageWithCreditDto emailMessage) {
        SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(emailMessage.address());
            message.setSubject("Кредитные уведомления");
            message.setText(String.format("Ваши документы: %s", emailMessage.creditDto().toString()));
        return message;
    }
}
