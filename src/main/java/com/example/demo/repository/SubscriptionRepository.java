package com.example.demo.repository;

import com.example.demo.entity.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {

    @Query("SELECT s FROM Subscription s WHERE s.user.id = :userId AND s.status = 'active' AND CURRENT_TIMESTAMP BETWEEN s.startDate AND s.endDate")
    Optional<Subscription> findActiveByUser(@Param("userId") Long userId);
    
    
    
    
}
