package com.example.demo.controller.general;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.demo.dto.SubscriptionDTO;
import com.example.demo.entity.Subscription;
import com.example.demo.entity.SubscriptionPlan;
import com.example.demo.entity.User;
import com.example.demo.repository.SubscriptionPlanRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.SubscriptionService;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@CrossOrigin(origins = "http://localhost:4200")

@RestController
@RequestMapping("/api/subscriptions")
public class SubscriptionController {

	@Autowired
	private SubscriptionService subscriptionService;


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SubscriptionPlanRepository subscriptionPlanRepository;
    
    
    
    

    @PostMapping("/create")
    public ResponseEntity<Subscription> createSubscription(@RequestBody SubscriptionDTO subscriptionDTO) {
        User user = userRepository.findById(subscriptionDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        SubscriptionPlan plan = subscriptionPlanRepository.findById(subscriptionDTO.getPlanId())
                .orElseThrow(() -> new RuntimeException("Plan not found"));

        Subscription subscription = subscriptionService.createSubscription(user, plan, subscriptionDTO);

        return ResponseEntity.ok(subscription);
    }


    
   
    
    

    @GetMapping("/canAddAlojamiento/{userId}")
    public ResponseEntity<Boolean> canAddAlojamiento(@PathVariable Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        return ResponseEntity.ok(subscriptionService.canUserAddAlojamiento(user));
    }
    
    @PostMapping("/createPending")
    public ResponseEntity<Subscription> createPendingSubscription(@RequestBody SubscriptionDTO subscriptionDTO) {
        User user = userRepository.findById(subscriptionDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        SubscriptionPlan plan = subscriptionPlanRepository.findById(subscriptionDTO.getPlanId())
                .orElseThrow(() -> new RuntimeException("Plan not found"));

        Subscription pendingSubscription = subscriptionService.createPendingSubscription(user, plan);
        return ResponseEntity.ok(pendingSubscription);
    }

    @GetMapping("/active/{userId}")
    public ResponseEntity<Subscription> getActiveSubscription(@PathVariable Long userId) {
        Optional<Subscription> activeSubscription = subscriptionService.getActiveSubscription(userId);
        return activeSubscription
            .map(ResponseEntity::ok)
            .orElseGet(() -> ResponseEntity.status(HttpStatus.NO_CONTENT).build());
    }
}