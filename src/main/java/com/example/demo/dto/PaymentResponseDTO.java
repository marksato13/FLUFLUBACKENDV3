package com.example.demo.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
public class PaymentResponseDTO {
    private Long id;
    private BigDecimal amount;
    private String paymentMethod;
    private Timestamp paymentDate;
    private String status;
    private String transactionId;
    
    
    private Long userId;  
    private Long subscriptionId;
    
    
    
    private Timestamp createdAt;
    private Timestamp updatedAt;
}
