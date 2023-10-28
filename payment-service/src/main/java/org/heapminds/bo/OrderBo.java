package org.heapminds.bo;

import java.util.Date;

import lombok.Data;


@Data
public class OrderBo {
    private int amount;
    private Date created_at;
    private String currency;
    private String receipt;
    private String id;
    private String entity;
    private String offer_id;
    private String status;

}