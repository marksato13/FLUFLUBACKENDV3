package com.example.demo.serviceImpl;

import com.example.demo.dto.SubscriptionDTO;
import com.example.demo.entity.Role;
import com.example.demo.entity.Subscription;
import com.example.demo.entity.SubscriptionPlan;
import com.example.demo.entity.User;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.SubscriptionRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.AlojamientoRepository;
import com.example.demo.service.SubscriptionService;

import jakarta.transaction.Transactional;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class SubscriptionServiceImpl implements SubscriptionService {

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private AlojamientoRepository alojamientoRepository;



    @Override
    public Subscription createPendingSubscription(User user, SubscriptionPlan plan) {
        Subscription subscription = new Subscription();
        subscription.setUser(user);
        subscription.setPlan(plan);
        subscription.setStatus("pending");

        subscription.setStartDate(Timestamp.valueOf(LocalDateTime.now()));
        subscription.setEndDate(Timestamp.valueOf(LocalDateTime.now().plusMonths(plan.getDurationInMonths())));

        return subscriptionRepository.save(subscription);
    }

    @Transactional
    @Override
    public void activateSubscription(Subscription subscription) {
        Hibernate.initialize(subscription.getPlan());
        subscription.setStatus("active");
        subscription.setStartDate(Timestamp.valueOf(LocalDateTime.now()));
        subscription.setEndDate(Timestamp.valueOf(LocalDateTime.now().plusMonths(subscription.getPlan().getDurationInMonths())));
        subscriptionRepository.save(subscription);
    }


    @Override
    public Subscription createSubscription(User user, SubscriptionPlan plan, SubscriptionDTO subscriptionDTO) {
        Subscription subscription = new Subscription();
        subscription.setUser(user);
        subscription.setPlan(plan);
        subscription.setStartDate(subscriptionDTO.getStartDate());
        subscription.setEndDate(subscriptionDTO.getEndDate());
        subscription.setStatus(subscriptionDTO.getStatus());

        subscription = subscriptionRepository.save(subscription);

        updateUserSubscriptionStatus(user, subscription);

        return subscription;
    }

    @Override
    public boolean canUserAddAlojamiento(User user) {
        Optional<Subscription> activeSubscription = subscriptionRepository.findActiveByUser(user.getId());
        if (activeSubscription.isPresent()) {
            SubscriptionPlan plan = activeSubscription.get().getPlan();
            long alojamientosCount = alojamientoRepository.countByUserId(user.getId());

            if ("Gratuito".equals(plan.getName()) && alojamientosCount >= 1) return false;
            if ("Estándar".equals(plan.getName()) && alojamientosCount >= 3) return false;
        }
        return true;
    }

    @Override
    public Optional<Subscription> getActiveSubscription(Long userId) {
        return subscriptionRepository.findActiveByUser(userId);
    }

    @Transactional
    @Override
    public void updateUserSubscriptionStatus(User user, Subscription subscription) {
        user.setHasActiveSubscription(true);
        user.setCurrentPlan(subscription.getPlan().getName());
        userRepository.save(user);
    }
}