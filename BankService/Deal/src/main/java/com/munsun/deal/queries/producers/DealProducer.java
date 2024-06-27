package com.munsun.deal.queries.producers;

import com.munsun.deal.dto.payload.enums.Theme;

import java.util.UUID;

public interface DealProducer {
    void sendFinishRegistrationRequestNotification(String email, Theme theme, UUID statementId);
    void sendPrepareDocumentsNotification(String email, Theme theme, UUID statementId);
    void sendSignCodeDocumentsNotification(String email, Theme theme, UUID statementId, String string);
    void sendSuccessSignDocumentsNotification(String email, Theme theme, UUID statementId, String creditIssued);
    void sendScoringException(String email, Theme theme, String s);
    void sendCreateDocumentsNotification(String email, Theme theme, String goToPaperwork);
}
