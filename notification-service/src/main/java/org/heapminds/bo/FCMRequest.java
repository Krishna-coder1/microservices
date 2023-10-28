package org.heapminds.bo;

import lombok.Data;

@Data
public class FCMRequest {

    private String to;
    private FCMNotification notification;
    private WebPush webpush;

    // Constructors, getters, and setters go here
    @Data
    public static class FCMNotification {
        private String title;
        private String body;
        private String icon;

        // Constructors, getters, and setters go here
    }

    public static class WebPush {
        private FCMDeliveryOptions fcm_options;

        // Constructors, getters, and setters go here

        public static class FCMDeliveryOptions {
            // You can add delivery options fields here if needed
        }
    }

    // Additional getters and setters if needed
}
