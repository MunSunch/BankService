package com.munsun.dossier.queries.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Setter
@Getter
@Configuration
@ConfigurationProperties(prefix = "kafka.topics")
public class KafkaTopics {
    private String finish_registration;
    private String create_documents;
    private String send_documents;
    private String send_ses;
    private String credit_issued;
    private String statement_denied;
}
