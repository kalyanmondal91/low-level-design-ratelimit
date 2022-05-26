package com.github.systemdesign.strategy.impl;

import com.github.systemdesign.strategy.LimitStrategy;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

@Service
public class TokenBucketStrategy implements LimitStrategy {

    @Builder
    @Getter
    @Setter
    public static class TokenBucket {
        Long lastTimestamp;
        Long tokenLeft;
    }

    private final long TIME_LIMIT = 100;
    private final long MAX_HITS = 5;
    private final Map<String, TokenBucket> bucket;

    public TokenBucketStrategy() {
        this.bucket = new HashMap<>();
    }

    @Override
    public boolean validateRequest(String clientId, Long timestamp) {
        if(bucket.containsKey(clientId)) {
            TokenBucket tokenBucket = bucket.get(clientId);
            Long prevTimestamp = tokenBucket.getLastTimestamp();
            int currentTimestampSlot = (int) (timestamp/TIME_LIMIT);
            int prevTimestampSlot = (int) (prevTimestamp/TIME_LIMIT);
            if(currentTimestampSlot == prevTimestampSlot) {
                if(tokenBucket.getTokenLeft() > 0) {
                    tokenBucket.setTokenLeft(tokenBucket.getTokenLeft() - 1);
                    bucket.put(clientId, tokenBucket);
                    return true;
                } else {
                    return false;
                }
            } else {
                tokenBucket.setTokenLeft(MAX_HITS - 1);
                bucket.put(clientId, tokenBucket);
                return true;
            }
        } else {
            TokenBucket tokenBucket = TokenBucket.builder().tokenLeft(MAX_HITS - 1).lastTimestamp(timestamp).build();
            bucket.put(clientId, tokenBucket);
            return true;
        }
    }
}
