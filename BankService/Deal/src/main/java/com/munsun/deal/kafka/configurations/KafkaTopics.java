package com.munsun.deal.kafka.configurations;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
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
