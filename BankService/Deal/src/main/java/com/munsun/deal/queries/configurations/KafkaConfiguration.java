package com.munsun.deal.queries.configurations;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
@RequiredArgsConstructor
@ConditionalOnExpression("#{${kafka.init.topics}==true}")
public class KafkaConfiguration {
    private final KafkaTopics topics;

    @Bean
    public NewTopic finishRegistrationTopic() {
        return TopicBuilder
                .name(topics.getFinish_registration())
                .build();
    }

    @Bean
    public NewTopic createDocumentsTopic() {
        return TopicBuilder
                .name(topics.getCreate_documents())
                .build();
    }

    @Bean
    public NewTopic sendDocumentsTopic() {
        return TopicBuilder
                .name(topics.getSend_documents())
                .build();
    }

    @Bean
    public NewTopic sendSesTopic() {
        return TopicBuilder
                .name(topics.getSend_ses())
                .build();
    }

    @Bean
    public NewTopic creditIssuedTopic() {
        return TopicBuilder
                .name(topics.getCredit_issued())
                .build();
    }

    @Bean
    public NewTopic statementDeniedTopic() {
        return TopicBuilder
                .name(topics.getStatement_denied())
                .build();
    }
}
