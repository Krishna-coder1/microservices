package org.heapminds.events.producer;

import java.util.HashMap;

import org.heapminds.bo.PaymentSuccessBO;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class PaymentSuccessProducer {

    private KafkaTemplate<String, PaymentSuccessBO> paymentTemplate;

    public PaymentSuccessProducer(KafkaTemplate<String, PaymentSuccessBO> paymentTemplate) {
        this.paymentTemplate = paymentTemplate;
    }

    public void sendMessageToNotificationTopic(PaymentSuccessBO paymentSuccessBO) {

        log.info("payment_success_event: {}", paymentSuccessBO.getAccount_id());
        Message<PaymentSuccessBO> message = MessageBuilder
                .withPayload(paymentSuccessBO)
                .setHeader(KafkaHeaders.TOPIC, "notification_topic")
                .build();
        paymentTemplate.send(message);

    }

    public void sendMessageToFcmTopic(HashMap fcmToken) {
        log.info("fcm_topic: {}", fcmToken);
        Message<HashMap> message = MessageBuilder
                .withPayload(fcmToken)
                .setHeader(KafkaHeaders.TOPIC, "fcm_topic")
                .build();
        paymentTemplate.send(message);
    }
}
