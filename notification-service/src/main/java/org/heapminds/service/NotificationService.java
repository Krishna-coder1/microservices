package org.heapminds.service;

import com.google.firebase.messaging.FirebaseMessagingException;

public interface NotificationService {
    public void subscribeUser(String token) throws FirebaseMessagingException;

    public void unsubscribeUser(String token) throws FirebaseMessagingException;

    public void notifyAllUsers() throws FirebaseMessagingException;
}
