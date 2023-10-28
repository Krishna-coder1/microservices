package org.heapminds.controller;

import org.heapminds.dto.SubscribeToNotificationsDto;
import org.heapminds.ro.BaseFro;
import org.heapminds.ro.BaseRo;
import org.heapminds.ro.BaseSro;
import org.heapminds.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.firebase.messaging.FirebaseMessagingException;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/api/notification")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @PostMapping("/subscribe")
    public BaseRo subscribeToNotifications(
            @RequestBody SubscribeToNotificationsDto toNotificationsDto) throws FirebaseMessagingException {
        try {
            notificationService.subscribeUser(toNotificationsDto.getFcmToken());
            return new BaseSro<SubscribeToNotificationsDto>(toNotificationsDto);
        } catch (FirebaseMessagingException e) {
            log.error(e.getMessage());
            return new BaseFro(e.getMessage());
        }
    }

    @PostMapping("/unsubscribe")
    public BaseRo unsubscribeToNotifications(
            @RequestBody SubscribeToNotificationsDto toNotificationsDto) throws FirebaseMessagingException {
        try {
            notificationService.subscribeUser(toNotificationsDto.getFcmToken());
            return new BaseSro<SubscribeToNotificationsDto>(toNotificationsDto);
        } catch (FirebaseMessagingException e) {
            log.error(e.getMessage());
            return new BaseSro<String>(e.getMessage());
        }
    }

}
