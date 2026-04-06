package com.udxp.kafka.service;

import com.udxp.kafka.UserBehaviorEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class KafkaProducerService {
    private final KafkaTemplate<String, Object> kafkaTemplate;
    public void sendUserBehaviorEvent(Long userId, Long movieId, String action){
        UserBehaviorEvent event = new UserBehaviorEvent(
                UUID.randomUUID().toString(),
                userId, movieId, action,
                System.currentTimeMillis());
        kafkaTemplate.send("user-behavior", event);
    }
}
