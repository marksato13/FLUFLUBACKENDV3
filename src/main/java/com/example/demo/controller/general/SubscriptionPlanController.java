package com.example.demo.controller.general;

import com.example.demo.entity.SubscriptionPlan;
import com.example.demo.service.SubscriptionPlanService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/subscription-plans")
public class SubscriptionPlanController {

    @Autowired
    private SubscriptionPlanService subscriptionPlanService;

    @PostMapping("/create")
    public ResponseEntity<SubscriptionPlan> createSubscriptionPlan(@RequestBody SubscriptionPlan plan) {
        SubscriptionPlan createdPlan = subscriptionPlanService.createSubscriptionPlan(plan);
        return ResponseEntity.ok(createdPlan);
    }
    @GetMapping("/list")
    public ResponseEntity<List<SubscriptionPlan>> listSubscriptionPlans() {
        List<SubscriptionPlan> plans = subscriptionPlanService.listAllPlans();
        return ResponseEntity.ok(plans);
    }
}