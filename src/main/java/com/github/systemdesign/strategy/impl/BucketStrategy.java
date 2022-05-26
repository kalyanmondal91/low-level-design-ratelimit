package com.github.systemdesign.strategy.impl;

import com.github.systemdesign.strategy.LimitStrategy;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

@Service
public class BucketStrategy implements LimitStrategy {

    private final long TIME_LIMIT = 100;
    private final long MAX_HITS = 5;
    private final Map<String, Queue<Long>> bucket;

    public BucketStrategy() {
        this.bucket = new HashMap<>();
    }

    @Override
    public boolean validateRequest(String clientId, Long timestamp) {
        if(bucket.containsKey(clientId)) {
            Queue<Long> timeLogs = bucket.get(clientId);
            while (!timeLogs.isEmpty() && timestamp - timeLogs.peek() >= TIME_LIMIT) {
                timeLogs.poll();
            }
            if(timeLogs.size() < MAX_HITS) {
                timeLogs.add(timestamp);
                bucket.put(clientId, timeLogs);
                return true;
            }
        } else {
            Queue<Long> timeLogs = new LinkedList<>();
            timeLogs.add(timestamp);
            bucket.put(clientId, timeLogs);
            return true;
        }
        return false;
    }
}
