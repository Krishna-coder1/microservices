package org.heapminds.service.implementation;

import java.util.ArrayList;
import java.util.List;

import org.heapminds.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class NotificationServiceImplementation implements NotificationService {

    private final FirebaseMessaging firebaseMessaging;

    @Autowired
    public NotificationServiceImplementation(FirebaseMessaging firebaseMessaging) {
        this.firebaseMessaging = firebaseMessaging;
    }

    @Override
    public void subscribeUser(String token) throws FirebaseMessagingException {
        try {
            List<String> tokens = new ArrayList<String>();
            tokens.add(0, token);
            firebaseMessaging.subscribeToTopic(tokens, "paymentTopic");
            log.info("Subscription successful");

        } catch (FirebaseMessagingException e) {
            log.error("ERROR_IN_SUBSCRIBING: {}", e.getMessage());
        }
    }

    @Override
    public void unsubscribeUser(String token) throws FirebaseMessagingException {
        try {
            List<String> tokens = new ArrayList<String>();
            tokens.add(0, token);
            firebaseMessaging.unsubscribeFromTopic(tokens, "paymentTopic");
            log.info("Subscription successful");

        } catch (FirebaseMessagingException e) {
            log.error("ERROR_IN_SUBSCRIBING: {}", e.getMessage());
        }
    }

    public void notifyAllUsers() throws FirebaseMessagingException {
        try {

            Notification notification = Notification.builder()
                    .setTitle("Payment Success")
                    .setImage(
                            "https://www.facebook.com/photo.php?fbid=1380498745497020&set=pb.100006107230227.-2207520000&type=3")
                    .setBody("Prem babu bounava").build();
            Message message = Message.builder()
                    .putData("score", "850")
                    .putData("time", "2:45")
                    .setNotification(notification)
                    .setTopic("paymentTopic")
                    .build();
            firebaseMessaging.send(message);
        } catch (FirebaseMessagingException e) {
            log.error("ERROR_IN_SENDING_NOTIF: {}", e.getMessage());
        }
    }

}
