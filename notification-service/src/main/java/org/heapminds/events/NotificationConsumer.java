package org.heapminds.events;

import java.util.HashMap;

import org.heapminds.bo.PaymentSuccessBO;
import org.heapminds.constants.KafkaTopics;
import org.heapminds.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.google.firebase.messaging.FirebaseMessagingException;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class NotificationConsumer {

    @Autowired
    private NotificationService notificationService;

    @KafkaListener(topics = KafkaTopics.FCM_TOPIC, groupId = "${spring.kafka.consumer.group-id}")
    public void consumeFcmToken(HashMap<String, String> token) {

        String fcmToken = token.get("fcmToken");
        log.info("FCM_TOKEN:{}", fcmToken);
    }

    @KafkaListener(topics = KafkaTopics.NOTIFICATION_TOPIC, groupId = "${spring.kafka.consumer.group-id}")
    public void consumePaymentBo(PaymentSuccessBO paymentBo) throws FirebaseMessagingException {
        try {
            log.info("PAYMENT_ACCOUNT_ID:{}", paymentBo.getAccount_id());
            notificationService.notifyAllUsers();
        } catch (FirebaseMessagingException e) {
            log.error("ERROR_IN_SENDING_NOTIFICATION: {}", e.getMessage());
        }
    }
}
