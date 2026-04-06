package com.udxp.kafka;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserBehaviorEvent {
    String eventId;
    Long userId;
    Long movieId;
    String action;
    Long timestamp;
}
