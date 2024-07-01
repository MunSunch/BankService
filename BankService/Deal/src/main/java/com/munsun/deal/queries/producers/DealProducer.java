package com.munsun.deal.queries.producers;

import com.munsun.deal.dto.response.CreditDto;
import com.munsun.deal.queries.payload.enums.Theme;

import java.util.UUID;

public interface DealProducer {
    void sendFinishRegistrationRequestNotification(String email, Theme theme, UUID statementId);
    void sendPrepareDocumentsNotification(String email, Theme theme, UUID statementId, CreditDto creditDto);
    void sendSignCodeDocumentsNotification(String email, Theme theme, UUID statementId, UUID sesCode);
    void sendSuccessSignDocumentsNotification(String email, Theme theme, UUID statementId);
    void sendScoringException(String email, Theme theme, UUID statementId);
    void sendCreateDocumentsNotification(String email, Theme theme, UUID statementId);
}
