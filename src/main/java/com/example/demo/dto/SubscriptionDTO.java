package com.example.demo.dto;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class SubscriptionDTO {
    private Long userId;
    private Long planId;
    private Timestamp startDate;
    private Timestamp endDate;
    private String status;
}
