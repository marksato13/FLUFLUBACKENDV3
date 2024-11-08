package com.example.demo.controller.general;

import com.example.demo.dto.PaymentDTO;
import com.example.demo.dto.PaymentResponseDTO;
import com.example.demo.entity.Payment;
import com.example.demo.entity.Subscription;
import com.example.demo.entity.User;
import com.example.demo.repository.SubscriptionRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.PaymentService;
import com.example.demo.service.SubscriptionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private SubscriptionService subscriptionService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SubscriptionRepository subscriptionRepository;
    
    
    
    
    
    @PostMapping("/create")
    public ResponseEntity<?> createPayment(@RequestBody PaymentDTO paymentDTO) {
        try {
            User user = userRepository.findById(paymentDTO.getUserId())
                    .orElseThrow(() -> new RuntimeException("User not found with ID: " + paymentDTO.getUserId()));

            Subscription subscription = subscriptionRepository.findById(paymentDTO.getSubscriptionId())
                    .orElseThrow(() -> new RuntimeException("Subscription not found with ID: " + paymentDTO.getSubscriptionId()));

            if (!"pending".equals(subscription.getStatus())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Error: La suscripción no está en estado pendiente. Estado actual: " + subscription.getStatus());
            }

            Payment payment = paymentService.createPayment(user, subscription, paymentDTO);

            subscriptionService.activateSubscription(subscription);

            user.setHasActiveSubscription(true);
            user.setCurrentPlan(subscription.getPlan().getName());
            userRepository.save(user); 

            PaymentResponseDTO responseDTO = convertToDTO(payment);
            return ResponseEntity.ok(responseDTO);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error interno: " + e.getMessage());
        }
    }

    

  
    
    
    
    
    

    private PaymentResponseDTO convertToDTO(Payment payment) {
        PaymentResponseDTO dto = new PaymentResponseDTO();
        dto.setId(payment.getId());
        dto.setAmount(payment.getAmount());
        dto.setPaymentMethod(payment.getPaymentMethod());
        dto.setPaymentDate(payment.getPaymentDate());
        dto.setStatus(payment.getStatus());
        dto.setTransactionId(payment.getTransactionId());
        dto.setUserId(payment.getUser().getId());
        dto.setSubscriptionId(payment.getSubscription().getId());
        dto.setCreatedAt(payment.getCreatedAt());
        dto.setUpdatedAt(payment.getUpdatedAt());
        return dto;
    }
}
