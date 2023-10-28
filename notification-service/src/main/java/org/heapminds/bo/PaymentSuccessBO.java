package org.heapminds.bo;


import java.util.Map;

import lombok.Getter;


@Getter
public class PaymentSuccessBO {
    private String entity;
    private String account_id;
    private String event;
    private String[] contains;
    private Payload payload;
    private long created_at;

    @Getter
    public static class Payload {
        private Payment payment;

        @Getter
        public static class Payment {
            private Entity entity;


            @Getter
            public static class Entity {
                private String id;
                private String entity;
                private Long amount;
                private String currency;
                private String status;
                private String order_id;
                private String invoice_id;
                private boolean international;
                private boolean captured;
                private String method;
                private long amount_refunded;
                private String refund_status;
                private String description;
                private String card_id;
                private String bank;
                private String wallet;
                private String vpa;
                private String email;
                private String contact;
                private Map<String, String> notes;
                private int fee;
                private int tax;
                private String error_code;
                private String error_description;
                private String error_source;
                private String error_step;
                private String error_reason;
                private AcquirerData acquirer_data;
                private long created_at;
                private Upi upi;
                private long base_amount;

                @Getter
                public static class AcquirerData {
                    private String rrn;
                    private String upi_transaction_id;

                    // Getters and setters for rrn and upi_transaction_id
                }

                @Getter
                public static class Upi {
                    private String vpa;

                    // Getter and setter for vpa
                }
            }
        }
    }
}
