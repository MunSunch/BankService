package com.munsun.deal.kafka.producers;

import com.munsun.deal.dto.CreditDto;
import com.munsun.deal.kafka.payload.enums.Theme;

import java.util.UUID;

public interface DealProducer {
    void sendFinishRegistrationRequestNotification(String email, Theme theme, UUID statementId);
    void sendPrepareDocumentsNotification(String email, Theme theme, UUID statementId, CreditDto creditDto);
    void sendSignCodeDocumentsNotification(String email, Theme theme, UUID statementId, UUID sesCode);
    void sendSuccessSignDocumentsNotification(String email, Theme theme, UUID statementId);
    void sendScoringException(String email, Theme theme, UUID statementId);
    void sendCreateDocumentsNotification(String email, Theme theme, UUID statementId);
}
