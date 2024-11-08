package com.example.demo.serviceImpl;

import com.example.demo.entity.SubscriptionPlan;
import com.example.demo.repository.SubscriptionPlanRepository;
import com.example.demo.service.SubscriptionPlanService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SubscriptionPlanServiceImpl implements SubscriptionPlanService {

    @Autowired
    private SubscriptionPlanRepository subscriptionPlanRepository;

    @Override
    public SubscriptionPlan createSubscriptionPlan(SubscriptionPlan plan) {
        return subscriptionPlanRepository.save(plan);
    }
    @Override
    public List<SubscriptionPlan> listAllPlans() {
        return subscriptionPlanRepository.findAll();
    }
}