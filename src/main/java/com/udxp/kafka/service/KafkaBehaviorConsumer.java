package com.udxp.kafka.service;

import com.udxp.kafka.entities.UserBehavior;
import com.udxp.kafka.repository.BehaviorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaBehaviorConsumer {
    private BehaviorRepository behaviorRepository;
    @KafkaListener(topics = "user-behavior", groupId = "behavior-group")
    public void consume(UserBehavior behavior) {
        UserBehavior userBehavior = new UserBehavior();
        userBehavior.setId(behavior.getId());
        userBehavior.setUserId(behavior.getUserId());
        userBehavior.setMovieId(behavior.getMovieId());
        userBehavior.setAction(behavior.getAction());
        behaviorRepository.save(userBehavior);
    }
}
