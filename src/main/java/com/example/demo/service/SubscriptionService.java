package com.example.demo.service;

import com.example.demo.dto.SubscriptionDTO;
import com.example.demo.entity.Subscription;
import com.example.demo.entity.SubscriptionPlan;
import com.example.demo.entity.User;
import java.util.Optional;

public interface SubscriptionService {
    Subscription createSubscription(User user, SubscriptionPlan plan, SubscriptionDTO subscriptionDTO);
    boolean canUserAddAlojamiento(User user);

    Subscription createPendingSubscription(User user, SubscriptionPlan plan);

    Optional<Subscription> getActiveSubscription(Long userId);

    void activateSubscription(Subscription subscription);

    void updateUserSubscriptionStatus(User user, Subscription subscription);
    
    
}
