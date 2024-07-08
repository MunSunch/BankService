package com.munsun.dossier.services.impl.providers;

import com.munsun.dossier.queries.payload.EmailMessageWithCreditDto;

import javax.activation.DataSource;

public interface DocumentGenerator {
    DataSource generateDocument(EmailMessageWithCreditDto emailMessage);
}
