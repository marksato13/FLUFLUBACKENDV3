package com.example.demo.service;

import com.example.demo.dto.PaymentDTO;
import com.example.demo.entity.Payment;
import com.example.demo.entity.Subscription;
import com.example.demo.entity.User;

public interface PaymentService {
    Payment createPayment(User user, Subscription subscription, PaymentDTO paymentDTO);
}
