package com.example.demo.service;

import java.util.List;

import com.example.demo.entity.SubscriptionPlan;

public interface SubscriptionPlanService {
    SubscriptionPlan createSubscriptionPlan(SubscriptionPlan plan);
    List<SubscriptionPlan> listAllPlans();  

}
